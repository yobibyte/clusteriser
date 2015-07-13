package com.yobibyte.io;

import com.yobibyte.DbHelper;
import com.yobibyte.ParamHolder;
import com.yobibyte.analysis.Util;
import com.yobibyte.structures.Fasta;
import org.neo4j.graphdb.GraphDatabaseService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FastaParser {

    public void parse(String filepath) {
        GraphDatabaseService db = ParamHolder.getInstance().getDb();
        try {
            Scanner sc = new Scanner(new File(filepath));

            Fasta currStruct = null;
            int ctr = 0;
            ParamHolder ph = ParamHolder.getInstance();

            while (sc.hasNext()) {
                String currString = sc.next();
                if (currString.contains(">")) {
                    if(currStruct != null) {
                        currStruct.setId(ctr++);
                        currStruct.getProteins().replaceAll("[^\\p{L}\\p{Nd}]", "");
                        if (ph.isRegisterCounted()) {
                            currStruct.getProteins().replaceAll("a-z", "-");
                        }
                        DbHelper.addFastaToDb(currStruct);
                    }
                    currStruct = new Fasta();
                    currStruct.generalInfo = currString;
                } else {
                    currStruct.proteins += currString;
                }
            }
            //not to forget the last one
            currStruct.setId(ctr);
            currStruct.getProteins().replaceAll("[^\\p{L}\\p{Nd}]", "");
            if (ph.isRegisterCounted()) {
                currStruct.getProteins().replaceAll("a-z", "-");
            }
            DbHelper.addFastaToDb(currStruct);
            ph.setInputSize(ctr);
            //here we believe that all the seqs in inpt have the same length
            ph.setAlignmentLen(DbHelper.getSequenceById(0).getProteins().length());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String[] removeGaps(String[] arrWithGaps) {
		int alignLen = arrWithGaps[0].length();
		int numOfSeq = arrWithGaps.length;

		int[] indexesToRemove = new int[alignLen];
		int indCtr = 0;
		for(int i = 0; i < alignLen; i++) {
			boolean isToRemove = true;
            for (String arrWithGap : arrWithGaps) {
                boolean isCurrCharGap = Util.isGap(arrWithGap.charAt(i));
                if (!isCurrCharGap) {
                    isToRemove = false;
                    break;
                }
            }
			if(isToRemove) {
				indexesToRemove[indCtr++] = i;
			} 
		}
		
		int numOfRemovals = indCtr;
		String[] res = new String[numOfSeq];
		StringBuilder sb;
		for(int i = 0; i < numOfSeq; i++) {
			sb = new StringBuilder(arrWithGaps[i]);
			int removalOffset = 0;
			for(int j = 0; j < numOfRemovals; j++) {
				sb.deleteCharAt(indexesToRemove[j] - removalOffset);
				removalOffset++;
			}
			res[i] = sb.toString();
		}
		return res;
	}
}
