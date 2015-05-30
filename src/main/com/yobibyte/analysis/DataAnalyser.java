package com.yobibyte.analysis;

import com.yobibyte.Constants;
import com.yobibyte.DbHelper;
import com.yobibyte.ParamHolder;
import com.yobibyte.io.FastaParser;
import com.yobibyte.io.FileIO;
import com.yobibyte.structures.Fasta;
import com.yobibyte.structures.LenClusteringResult;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DataAnalyser {

    static Logger log = Logger.getLogger(Data.class.getName());
    static ParamHolder ph = ParamHolder.getInstance();

    public static void makeCycle() {
        FastaParser parser = new FastaParser();
        parser.parse(ph.getFilepath());

        //first length clustering
        List<Fasta> sequences = new ArrayList<>();
        for(Fasta elem: DbHelper.getAllNodes()) {
            sequences.add(elem.clone());
        }

        List<LenClusteringResult> firstLenResult = clusterByLengthByGapValue(sequences, "", null, true);
        List<LenClusteringResult> secondLenResult = new ArrayList<>();

        //remove small chars and cluster by len one more time
        for(LenClusteringResult res : firstLenResult) {
            for (Fasta fasta : res.getCluster()) {
                fasta.setProteins(fasta.getProteins().replaceAll("[a-z]", "-"));
            }
            //remove gaps before second length clustering
            //Otherwise we just have NS_NS, FS_FS and NE_NE clusters
            Util.removeGaps(res.getCluster());
            secondLenResult.addAll(clusterByLengthByGapValue(res.getCluster(), res.getType(), ph.getGapInChars(), false));
        }

        for(LenClusteringResult res : secondLenResult) {
            if(!res.getCluster().isEmpty()) {
                List<Fasta> cluster = res.getCluster();
                processCluster(cluster, res.getType());
            }
        }

        FileIO.aggregateRepsFile(true);
        FileIO.aggregateRepsFile(false);
    }

	private static void processCluster(List<Fasta> sequences, String groupName) {
		log.info("Forming ajancency matrix...");
		int[][] adjMatrix = GraphUtils.formAdjacencyMatrix(sequences);
        log.info("Searching for connected components...");
		List<int[]> clusters = GraphUtils.findConnectedComponents(adjMatrix);
        int numOfClusters = clusters.size();
        log.info("Group " + groupName + " has " + numOfClusters + " clusters");
        //Will we have a misc cluster here?
        int miscComponensCtr = clusters.size();
        boolean isMisc = false;
        if(miscComponensCtr > 1) {
            isMisc = true;
            clusters = formMiscCluster(clusters);
        }
        log.info("Writing results to files...");
		FileIO fileIo = new FileIO();
		fileIo.writeToFileByClusters(groupName, clusters, sequences, isMisc, false);
        fileIo.writeToFileByClusters(groupName, clusters, sequences, isMisc, true);
		getMostPopularParts(clusters, adjMatrix, groupName, sequences, isMisc, false);
        getMostPopularParts(clusters, adjMatrix, groupName, sequences, isMisc,  true);

        FileIO.makePopularStatFile(sequences, groupName);
	}


    /*
        Forms adjacency matrix for cluster from the whole adj matrix
     */
    public static int[][] getAdjMatrixForCluster(int[][] oldAdjMatrix, int[] clusterElemsIds) {
		int numOfElems = clusterElemsIds.length;
		int adjMatrix[][] = new int[numOfElems][numOfElems];
		for(int i = 0; i < numOfElems; i++) {
			for(int j = 0; j < numOfElems; j++) {
				adjMatrix[i][j] = oldAdjMatrix[clusterElemsIds[i]][clusterElemsIds[j]];
				adjMatrix[j][i] = adjMatrix[i][j];
			}
		}
		return adjMatrix;
	}

    public static List<int[]> formMiscCluster(final List<int[]> clusters) {

        List<int[]> toSort = new ArrayList<>();

        for(int[] elem : clusters) {
            toSort.add(elem.clone());
        }

        Collections.sort(toSort, new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] ints2) {
                if (ints.length > ints2.length) {
                    return -1;
                } else if (ints.length < ints2.length) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int[] misc = new int[0];
        List<int[]> res = new ArrayList<>();
        for(int i = 0; i < clusters.size(); i++) {
            int[] curr = toSort.get(i);
            if(curr.length > Constants.MIN_SEQ_IN_CLUSTER) {
                res.add(curr);
            } else {
                int[] tmp = misc.clone();
                misc = new int[tmp.length + curr.length];
                System.arraycopy(tmp, 0, misc, 0, tmp.length);
                System.arraycopy(curr,0, misc, tmp.length, curr.length);
            }
        }
        if(misc.length != 0) {
            res.add(misc);
        }

        return res;
    }

	private static void getMostPopularParts(List<int[]> clusters, int[][] oldAdjMatrix, String groupName, List<Fasta> sequences, boolean isWithMisc, boolean isOrig) {
		final int NUM_OF_CLUSTERS = clusters.size();
		int[] aggregatedComponents = new int[NUM_OF_CLUSTERS];
		for (int i = 0; i < NUM_OF_CLUSTERS; i++) {
            int[] currentCluster = clusters.get(i);
			int mostPopularElementInCluster = 0;
			int[][] adjMatrix = getAdjMatrixForCluster(oldAdjMatrix, currentCluster);
			int clusterSize = adjMatrix.length;
			int maxSimRank = 0;
			for(int k = 0; k < clusterSize; k++) {
				int similarityRank = 0;
				for(int j = 0; j < clusterSize; j++) {
					similarityRank += adjMatrix[k][j];
				}
				if (maxSimRank < similarityRank) {
					maxSimRank = similarityRank;
					mostPopularElementInCluster = k;
				}
			}
			aggregatedComponents[i] = currentCluster[mostPopularElementInCluster];
		}

		int maxProtLength = 0;
        for(int comp : aggregatedComponents) {
            if(sequences.get(comp).proteins.length() > maxProtLength) {
                maxProtLength = sequences.get(comp).proteins.length();
            }
        }
        List<Fasta> popular = new ArrayList<>();
        if(isOrig) {
            for(int elem : aggregatedComponents) {
                //TODO optimize this shit
                try {
                    popular.add(DbHelper.getSequenceById(sequences.get(elem).getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for(int elem : aggregatedComponents) {
                popular.add(sequences.get(elem).clone());
            }
        }
        popular = Util.removeGaps(popular);
		List<String> aggregatedString = new ArrayList<>();
        //here we add cluster name to general info. This operation
        //is sensible to popular list order
        for (int i = 0; i < popular.size(); i++) {
            Fasta elem = popular.get(i);
            if(isWithMisc && i == (popular.size()-1)) {
                //TODO remove misc cluster in generating mostPopular files. Not just hide it.
                //aggregatedString.add(elem.generalInfo + '_' + groupName + "_misc\n" + elem.getProteins());
            } else {
                String tmp = elem.generalInfo + '_' + groupName + '_' + Integer.toString(i) + '\n' + elem.getProteins();
                aggregatedString.add(tmp);
            }
        }

		FileIO fileIo = new FileIO();
		fileIo.writeToFile("mostPopularParts_" + groupName + ".fasta", aggregatedString, isOrig);
	}

    public static List<LenClusteringResult> clusterByLengthByGapValue(List<Fasta> data, String groupName, Integer gap, boolean withLowercase) {

        List<Fasta> wsList = new ArrayList<>();
        List<Fasta> weList = new ArrayList<>();
        List<Fasta> fsList = new ArrayList<>();

        ParamHolder ph = ParamHolder.getInstance();

        if(gap == null) {
            gap = ph.getGapInChars();
        }
        Collections.sort(data);

        for(Fasta seq : data) {
            String prot = seq.getProteins();
            int protlen = prot.length();
            //TODO ACHTUNG!!! CHECK THOROUGLY! Secoud parameter shoud be changed.
            int firstAlphaInd = Util.getAlphaIndex(prot, withLowercase, withLowercase, true);
            int lastAlphaInd = Util.getAlphaIndex(prot, withLowercase, withLowercase, false);
            if((firstAlphaInd < gap) && ((protlen - lastAlphaInd) < gap)) {
                fsList.add(seq);
                continue;
            }
            if(firstAlphaInd > (protlen - lastAlphaInd)) {
                wsList.add(seq);
            } else {
                weList.add(seq);
            }
        }

        //TODO move to enum
        String[] grNames = {"NS", "NE", "FS"};
        if(!groupName.isEmpty()) {
            grNames = new String[]{"_NS", "_NE", "_FS"};
        }

        List<LenClusteringResult> toReturn = new ArrayList<>();
        if(!wsList.isEmpty()) {
            toReturn.add(new LenClusteringResult(wsList, groupName + grNames[0]));
        }
        if(!weList.isEmpty()) {
            toReturn.add(new LenClusteringResult(weList, groupName + grNames[1]));
        }
        if(!fsList.isEmpty()) {
            toReturn.add(new LenClusteringResult(fsList, groupName + grNames[2]));
        }
        return toReturn;
    }

}
