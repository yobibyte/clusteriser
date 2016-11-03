package com.yobibyte.analysis;

import com.yobibyte.ParamHolder;
import com.yobibyte.io.FastaParser;
import com.yobibyte.structures.Fasta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yobibyte on 11.03.15.
 */
public class Util {


    private static final Map<Character, String> alphabet;
    private static final boolean isRegisterCounted;
    private static final Double SIMILARITY_THRESHOLD;

    static {
        ParamHolder ph = ParamHolder.getInstance();
        alphabet = ph.getAlphabet();
        isRegisterCounted = ph.isRegisterCounted();
        SIMILARITY_THRESHOLD = ph.getSimilarityCoefficient();
    }

    // COMPRESSED ALPHABET
    public static boolean isSimilar(final String seq1, final String seq2) {
        // Here we iterate for full length even including non-letter chars
        final int checkLen = Math.min(seq1.length(), seq2.length());
        // For similarity coeff we use real length without non-letter chars
        final int realLen = getRealLength(seq1, seq2);
        int numOfBingo = 0;
        char[] seq1Arr = seq1.toCharArray();
        char[] seq2Arr = seq2.toCharArray();

        for (int i = 0; i < checkLen; i++) {
            char c1 = seq1Arr[i];
            char c2 = seq2Arr[i];
            boolean isC1Letter = !isGap(c1);
            if (isCharsSimilar(c1, c2) && isC1Letter) {
                numOfBingo++;
            }
        }
        return ((double) numOfBingo) / realLen > SIMILARITY_THRESHOLD;
    }

    /*
	 * Returns real length of an alignment according to next rules: if at i
	 * position both sequences have gap, then this position is not counted else
	 * the position is counted
	 */
    public static int getRealLength(final String seq1, final String seq2) {
        char[] seq1Ch = seq1.toCharArray();
        char[] seq2Ch = seq2.toCharArray();

        int seqLen = seq1.length();
        int realLength = 0;

        for(int i = 0; i < seqLen; i++) {
            if(!isGap(seq1Ch[i]) || !isGap(seq2Ch[i])) {
                realLength++;
            }
        }
        return realLength;
    }

    private static boolean isCharsSimilar(char c1, char c2) {
        if(c1==c2) {
            return true;
        }
        String similarLetters = alphabet.get(Character.toUpperCase(c1));
        if (similarLetters == null) {
            return false;
        }
        //TODO register is not counted here.
        return (similarLetters.contains(Character.toString(c2).toUpperCase()));
    }

    public static boolean isGap(final char toCheck) {
        if(!Character.isLetter(toCheck)) {
            return true;
        }
        if (isRegisterCounted) {
            return !Character.isUpperCase(toCheck);
        }
        return false;
    }

    public static int getLetterCount(String str, boolean isLowercase) {
        int ctr = 0;
        for(char c : str.toCharArray()) {
            if(Character.isAlphabetic(c)) {
                if(isLowercase && Character.isLowerCase(c)) {
                    ctr++;
                }
                if(!isLowercase && Character.isUpperCase(c)) {
                    ctr++;
                }
            }
        }
        return ctr;
    }

    /*
    return -1 if there is no letter for our request
     */
    public static int getAlphaIndex(String str, boolean isCaseSensitive, Boolean isLowerCase, boolean fromLeftToRight) {

        String req = str;
        int ind = -1;

        if(!fromLeftToRight) {
            req = new StringBuilder(str).reverse().toString();
        }


        for(int i = 0; i < str.length(); i++) {
            char c = req.charAt(i);

            if(!isCaseSensitive) {
                if(Character.isAlphabetic(c)) {
                    ind = i;
                    break;
                }
            } else if (Character.isAlphabetic(c)) {
                if(isLowerCase && !Character.isUpperCase(c)) {
                    ind = i;
                    break;
                }
                if(!isLowerCase && Character.isUpperCase(c)) {
                    ind = i;
                    break;
                }
            }
        }

        if(!fromLeftToRight) {
            ind = str.length() - 1 - ind;
        }
        return ind;
    }

    public static List<Fasta> removeGaps(List<Fasta> seqs) {
        List<Fasta> result = new ArrayList<>();
        String[] seqsWithGaps = new String[seqs.size()];
        for (int i = 0; i< seqs.size(); i++) {
            seqsWithGaps[i] = seqs.get(i).getProteins();
        }
        String[] seqsWithoutGaps = FastaParser.removeGaps(seqsWithGaps);

        for(int i = 0; i < seqs.size(); i++) {
            Fasta initSeq = seqs.get(i);
            Fasta currSeq = new Fasta();
            currSeq.setId(initSeq.getId());
            currSeq.setGeneralInfo(initSeq.getGeneralInfo());
            currSeq.setProteins(seqsWithoutGaps[i]);
            currSeq.setLength(initSeq.getLength());
            result.add(currSeq);
        }

        return result;
    }
}
