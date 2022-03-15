package com.gdf;

import java.util.ArrayList;
import java.util.List;

public class GDFGraph {

    List<DFNode> dfNodes;

    public GDFGraph() {
        this.dfNodes = new ArrayList<>();
    }

    public List<DFNode> getDfNodes() {
        return dfNodes;
    }

    public void setDfNodes(List<DFNode> dfNodes) {
        this.dfNodes = dfNodes;
    }

    public void addDfNodes(DFNode dfNode) {
        this.dfNodes.add(dfNode);
    }

    public void removeDfNodes(DFNode dfNode) {
        this.dfNodes.remove(dfNode);
    }

    @Override
    public String toString() {
        return "GDFGraph{" +
                "dfNodes=" + dfNodes +
                '}';
    }
}
