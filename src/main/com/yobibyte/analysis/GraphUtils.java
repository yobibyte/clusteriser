package com.yobibyte.analysis;

import com.yobibyte.structures.Fasta;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphUtils {

    static Logger log = Logger.getLogger(GraphUtils.class.getName());

    public static List<Integer> getAdjacentVertices(int forWhom, int[][] adjacencyMatrix) {
        List<Integer> res = new ArrayList<>();

        int numOfVert = adjacencyMatrix.length;
        for (int i = 0; i < numOfVert; i++) {
            if (adjacencyMatrix[forWhom][i] == 1) {
                res.add(i);
            }
        }
        return res;
    }

	public static int[][] formAdjacencyMatrix(final List<Fasta> seqList) {

		int numOfSeq = seqList.size();
		int[][] adjMatrix = new int[numOfSeq][numOfSeq];

		for (int i = 0; i < numOfSeq; i++) {
			String currSeqProts = seqList.get(i).getProteins();

			for (int j = i; j < numOfSeq; j++) {
				if (Util.isSimilar(currSeqProts, seqList.get(j).proteins)) {
					adjMatrix[i][j] = 1;
					adjMatrix[j][i] = 1;
				}
			}

            if(i%100 == 0) {
                log.info("Processing " + i + " sequence out of " + numOfSeq);
            }
		}

		return adjMatrix;
	}

    public static List<Integer> dfs(int[][] ajacencyMatrix, int from) {

        List<Integer> reachable = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        boolean[] isVisited = new boolean[ajacencyMatrix.length];

        stack.push(from);
        while (!stack.isEmpty()) {
            int currVer = stack.pop();
            if (!reachable.contains(new Integer(currVer))) {
                reachable.add(currVer);
            }
            if (!isVisited[currVer]) {
                isVisited[currVer] = true;
                for (int v : getAdjacentVertices(currVer, ajacencyMatrix)) {
                    stack.push(v);
                }
            }
        }
        return reachable;
    }

    public static List<int[]> findConnectedComponents(int[][] adjacencyMatrix) {
        int numOfVert = adjacencyMatrix.length;
        boolean[] isVisited = new boolean[numOfVert];
        int ctr = 0;
        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < numOfVert; i++) {
            if (!isVisited[i]) {
                List<Integer> reachable = dfs(adjacencyMatrix, i);
                int numOfReachable = reachable.size();
                int[] cluster = new int[numOfReachable];
                for (int j = 0; j < numOfReachable; j++) {
                    isVisited[reachable.get(j)] = true;
                    cluster[j] = reachable.get(j);
                }
                res.add(ctr, cluster);
                ctr++;
            }
        }
        return res;
    }



}