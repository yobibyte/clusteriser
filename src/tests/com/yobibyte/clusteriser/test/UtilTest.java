package com.yobibyte.clusteriser.test;

import com.yobibyte.ParamHolder;
import com.yobibyte.analysis.CompressedAlphabet;
import com.yobibyte.analysis.Util;
import com.yobibyte.structures.Fasta;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilTest extends Assert {


    @Test
     public void testIsSimilarA() {
        ParamHolder ph = ParamHolder.getInstance();
        ph.setAlphabet(CompressedAlphabet.REGULAR);
        ph.setSimilarityCoefficient(1);
        boolean res = Util.isSimilar("AAAA", "AAAB");
        assertEquals(false, res);
    }

    @Test
    public void testIsSimilarB() {
        ParamHolder ph = ParamHolder.getInstance();
        ph.setAlphabet(CompressedAlphabet.REGULAR);
        ph.setSimilarityCoefficient(0.6);
        boolean res = Util.isSimilar("AAAA", "AAAB");
        assertEquals(true, res);
    }

    @Test
    public void testGetRealLength() {
        String first = "A-B-C";
        String second = "A-GGC";
        int res = Util.getRealLength(first, second);
        assertEquals(4, res);

        first = "-";
        second = "-";
        res = Util.getRealLength(first, second);
        assertEquals(0, res);

        first = "--";
        second = "-A";
        res = Util.getRealLength(first, second);
        assertEquals(1, res);

        first = "F";
        second = "G";
        res = Util.getRealLength(first, second);
        assertEquals(1, res);
    }

    @Test
    public void testIsGap() {
        assertEquals(true, Util.isGap('-'));
        assertEquals(true, Util.isGap('.'));
        assertEquals(false, Util.isGap('a'));
    }

    @Test
    public void testGetLetterCount() {
        assertEquals(5, Util.getLetterCount("abcde", true));
        assertEquals(4, Util.getLetterCount("abCde", true));
        assertEquals(1, Util.getLetterCount("Abcde", false));
        assertEquals(0, Util.getLetterCount("", false));
    }


    @Test
    public void testGetAlphaIndex() {
        assertEquals(2, Util.getAlphaIndex("--aA", true, true, true));
        assertEquals(3, Util.getAlphaIndex("--aA", true, false, true));
        assertEquals(3, Util.getAlphaIndex("--aA", true, true, false));
        assertEquals(4, Util.getAlphaIndex("--aA", false, false, false));
    }

    @Test
    public void testRemoveGaps() {
        Fasta seq1 = new Fasta();
        Fasta seq2 = new Fasta();
        Fasta seq3 = new Fasta();
        seq1.setProteins("a-b");
        seq2.setProteins("c--");
        seq3.setProteins("d--");

        List<Fasta> seqs = new ArrayList<>();
        seqs.add(seq1);
        seqs.add(seq2);
        seqs.add(seq3);

        List<Fasta> res = Util.removeGaps(seqs);

        assertTrue(res.get(0).getProteins().equals("ab"));
        assertTrue(res.get(1).getProteins().equals("c-"));
        assertTrue(res.get(2).getProteins().equals("d-"));
    }

}
