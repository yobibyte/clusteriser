package com.yobibyte.clusteriser.test;

import com.yobibyte.analysis.GraphUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphUtilsTest extends Assert {


    @Test
    public void testGetAdjacentVertices() {
        int[][] adjMatrix = new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 0, 1}};
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        boolean isEqual = expected.equals(GraphUtils.getAdjacentVertices(1, adjMatrix));
        assertTrue(isEqual);
    }

    @Test
    public void testDfs() {
        int[][] adjMatrix = new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 0, 1}};
        List<Integer> toCheck = new ArrayList<>();
        toCheck.add(0);
        toCheck.add(2);
        toCheck.add(1);
        assertEquals(toCheck, GraphUtils.dfs(adjMatrix, 0));
    }

    @Test
    public void testFindConnectedComponents() {
        int[][] adjMatrix = new int[][]{{1, 1, 0, 0}, {1, 1, 0, 0},
                {0, 0, 1, 1}, {0, 0, 1, 1}};
        List<int[]> res = GraphUtils.findConnectedComponents(adjMatrix);

        List<int[]> toCheck = new ArrayList<>();
        toCheck.add(new int[]{0, 1});
        toCheck.add(new int[]{2, 3});

        assertEquals(toCheck, res);

    }

}
