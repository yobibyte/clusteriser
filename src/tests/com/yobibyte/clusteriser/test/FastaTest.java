package com.yobibyte.clusteriser.test;

import com.yobibyte.structures.Fasta;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by yobibyte on 20/04/15.
 */
public class FastaTest {
    @Test
    public void testCompareTo() {
        Fasta f1 = new Fasta();
        Fasta f2 = new Fasta();

        f1.setProteins("ABC");
        f1.setLength(3);
        f2.setProteins("AB");
        f2.setLength(2);
        assertEquals(1,f1.compareTo(f2));

        f1.setProteins("AB");
        f1.setLength(2);
        f2.setProteins("ABC");
        f2.setLength(3);
        assertEquals(-1,f1.compareTo(f2));

        f1.setProteins("AB");
        f1.setLength(2);
        f2.setProteins("AB");
        f2.setLength(2);
        assertEquals(0,f1.compareTo(f2));

    }
}
