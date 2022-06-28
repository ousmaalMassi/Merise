package com.models.gdf;

import java.util.ArrayList;
import java.util.List;

public class GDFGraph {

    List<GDFNode> dfNodes;

    public GDFGraph() {
        this.dfNodes = new ArrayList<>();
    }

    public List<GDFNode> getDfNodes() {
        return dfNodes;
    }

    public void setDfNodes(List<GDFNode> dfNodes) {
        this.dfNodes = dfNodes;
    }

    public void addDfNodes(GDFNode dfNode) {
        this.dfNodes.add(dfNode);
    }

    public void remove(GDFNode dfNode) {
        this.dfNodes.remove(dfNode);
    }

    public GDFNode contains(String string) {
        return this.dfNodes.stream()
                .filter(dfNode -> dfNode.getName().equals(string))
                .findAny()
                .orElse(null);
    }

    public void setName(GDFNode dfNode, String newName) {
        dfNode.setName(newName);
    }

    @Override
    public String toString() {
        return "GDFGraph{" +
                "dfNodes=" + dfNodes +
                '}';
    }
}
