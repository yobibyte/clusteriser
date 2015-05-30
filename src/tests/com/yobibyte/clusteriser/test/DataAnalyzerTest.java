package com.yobibyte.clusteriser.test;

import com.yobibyte.analysis.DataAnalyser;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by yobibyte on 07/04/15.
 */
public class DataAnalyzerTest {

    @Test
    public void testGetAdjMatrixForCluster() {
        int[] cluster = {0, 2, 3};
        int[][] adjMatrix = {{1,0,1,0,0}, {0,1,0,1,1}, {1,0,1,0,0}, {0,1,0,1,1}, {0,1,0,1,1}};
        int[][] expected = {{1,1,0},{1,1,0},{0,0,1}};
        int[][] res = DataAnalyser.getAdjMatrixForCluster(adjMatrix,cluster);
        assertArrayEquals(expected,res);
    }

}
