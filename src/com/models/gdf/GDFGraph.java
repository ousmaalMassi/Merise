package com.models.gdf;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GDFGraph implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<GDFNode> dfNodes;
    private final List<ComposedNode> composedNodes;

    public GDFGraph() {
        this.dfNodes = new ArrayList<>();
        this.composedNodes = new ArrayList<>();
    }

    public List<GDFNode> getDfNodes() {
        return dfNodes;
    }

    public List<ComposedNode> getComposedNodes() {
        return composedNodes;
    }

    public void addDfNode(GDFNode dfNode) {
        this.dfNodes.add(dfNode);
    }

    public void removeDfNode(GDFNode dfNode) {
        this.dfNodes.remove(dfNode);
    }

    public void addComposedNode(ComposedNode dfNode) {
        this.composedNodes.add(dfNode);
    }

    public void removeComposedNode(ComposedNode dfNode) {
        this.composedNodes.remove(dfNode);
    }

    public GDFNode containsAttribute(String string) {
        return this.dfNodes.stream()
                .filter(dfNode -> dfNode.getName().equals(string))
                .findAny()
                .orElse(null);
    }

    public ComposedNode containsComposedNode(String string) {
        return this.composedNodes.stream()
                .filter(composedNode -> composedNode.getName().equals(string))
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
