package com.yobibyte.structures;

import java.util.List;

/**
 * Created by yobibyte on 02.02.15.
 */
public class LenClusteringResult {
    private String name;
    private List<Fasta> cluster;

    public LenClusteringResult(List<Fasta> cluster, String name) {
        this.cluster = cluster;
        this.name = name;
    }

    public String getType() {
        return name;
    }

    public void setType(String name) {
        this.name = name;
    }

    public List<Fasta> getCluster() {
        return cluster;
    }

    public void setCluster(List<Fasta> cluster) {
        this.cluster = cluster;
    }
}
