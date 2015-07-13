package com.yobibyte.io;

import com.yobibyte.DbHelper;
import com.yobibyte.ParamHolder;
import com.yobibyte.analysis.Util;
import com.yobibyte.structures.Fasta;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIO {

    private static Logger log;

    static {
        log = Logger.getLogger(FileIO.class.getName());
    }

    public static void aggregateRepsFile(boolean isOrig) {

        String workingDir = ParamHolder.getInstance().getResultFolder().getAbsolutePath();
        if(isOrig) {
            workingDir += "/orig";
        } else {
            workingDir += "/trimmed";
        }

        File dir = new File(workingDir);
        FileFilter fileFilter = new WildcardFileFilter("mostPopularParts_*.fasta");
        File[] files = dir.listFiles(fileFilter);
        try {
            IOCopier.joinFiles(new File(workingDir + "/mostPopularAggregated.fasta"), files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makePopularStatFile(List<Fasta> sequences, String groupName) {
        BufferedWriter bw;
        ParamHolder ph = ParamHolder.getInstance();

        try {
            File f = new File(ph.getResultFolder().getAbsolutePath() + "/stat_" + groupName + ".txt");
            bw = new BufferedWriter(new FileWriter(f));

           bw.write(String.format(
                    "%s | %s | %s | %s | %s | %s | %s | %s | %s |\n", String.format("%1$25s", "name"), "firstLetterId",
                    "firstBigLetterId", "lastBigLetterId", "LastLetterId", "leftSmall",
                    "centerSmall", "centerBig", "rightSmall"));
            bw.write(String.format("------------------------------------------------------------------------------------------------------" +
                    "--------------------------------------------\n"));

            for(Fasta seq : sequences) {
                String prot = null;
                try {
                    prot = DbHelper.getSequenceById(seq.getId()).getProteins();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int firstAlphaId = Util.getAlphaIndex(prot, false, null, true);
                int lastAlphaId = Util.getAlphaIndex(prot, false, null, false);
                int leftUpperAlphaId = Util.getAlphaIndex(prot, true, false, true);
                int rightUpperAlphaId = Util.getAlphaIndex(prot, true, false, false);

                //prot is divided into three parts - from first small to first big, from first big
                //to last big, from last big to last small - A,B,C
                String tmp = prot.substring(firstAlphaId, leftUpperAlphaId + 1);
                int aLowerNum = Util.getLetterCount(tmp, true);

                int cLowerNum = 0;
                int bLowerNum = 0;
                int bUpperNum = 0;
                //FIXME change calcs

                tmp = prot.substring(leftUpperAlphaId + 1, rightUpperAlphaId + 1);
                bLowerNum = Util.getLetterCount(tmp, true);
                bUpperNum = Util.getLetterCount(tmp, false);
                tmp = prot.substring(rightUpperAlphaId, lastAlphaId);
                cLowerNum = Util.getLetterCount(tmp, true);

                bw.write(String.format("%s |%14d | %16d | %15d | %12d | %9d | %11d | %9d | %10d |\n", String.format("%1$25s", seq.getGeneralInfo()),
                        firstAlphaId, leftUpperAlphaId, lastAlphaId, rightUpperAlphaId,
                        aLowerNum, bLowerNum, bUpperNum, cLowerNum));
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void writeToFile(String filename, List<String> content, boolean isOrig) {
		BufferedWriter bw;

		ParamHolder ph = ParamHolder.getInstance();
        File folder;
        if(isOrig) {
            folder = ph.getOrig();
        } else {
            folder = ph.getTrimmed();
        }
		try {
			File f = new File(folder.getAbsolutePath() + "/" + filename);
			bw = new BufferedWriter(new FileWriter(f));
			for (String str : content) {
				bw.newLine();
				bw.write(str);
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static void writeToFileByClusters(String groupName, List<int[]> clusters, List<Fasta> sequences, boolean isMisc, boolean isOrig) {
        int numOfFiles = clusters.size();
        List<Fasta> toWrite = new ArrayList<>();

        //TODO optimize this shit
        if(isOrig) {
            for(Fasta elem : sequences) {
                //FIXME WHY originals are not sorted by id here?
                Fasta orig = null;
                try {
                    orig = DbHelper.getSequenceById(elem.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                toWrite.add(orig.clone());
            }
        } else {
            for(Fasta elem : sequences) {
                toWrite.add(elem.clone());
            }
        }

        try {

            ParamHolder ph = ParamHolder.getInstance();

            for(int i = 0; i < numOfFiles; i++) {
                String num;
                if(isMisc && i == numOfFiles - 1) {
                    num = "misc";
                } else {
                    num = Integer.toString(i);
                }
                File folder;
                if(isOrig) {
                    folder = ph.getOrig();

                } else {
                    folder = ph.getTrimmed();
                }
                String filename = folder.getAbsolutePath() + "/" + groupName + "_"
                        + num + ".fasta";
                File file = new File(filename);
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                //TODO optimize by moving anywhere else
                List<Fasta> currCluster = new ArrayList<>();
                for(int id : clusters.get(i)) {
                    currCluster.add(toWrite.get(id));
                }
                currCluster = Util.removeGaps(currCluster);
                //here we get seqs with removed empty columns
                for(Fasta currSeq : currCluster) {
                    bw.write(currSeq.generalInfo);
                    bw.newLine();
                    bw.write(currSeq.proteins);
                    bw.newLine();
                }

                bw.flush();
                bw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public static Map<Character, String> parseCompressedAlphabetFromFile(String filepath) {
		File fileToRead = new File(filepath);
		Map<Character, String> toReturn = new HashMap<>();
		
		try {
            FileReader fileReader = new FileReader(fileToRead);
            BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine().toUpperCase();
			String[] reducedGroups = line.split(",");
			
			for(String group : reducedGroups) {
				int groupLen = group.length();
				for(int i = 0; i < groupLen; i++) {
					toReturn.put(group.charAt(i), group);
				}
			}
			log.info("Using custom alphabet:" + toReturn.toString());
			br.close();
		} catch (FileNotFoundException e) {
			log.error("Sorry, file with alphabet was not found. So I use regular alphabet.");
		} catch (IOException e) {
            log.error("Sorry, something is wrong with your alphabet. So I use regular alphabet.");
		} 
		
		return toReturn;
	}
	

}
