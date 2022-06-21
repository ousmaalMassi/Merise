package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphicalNode;
import com.model.gdf.GDFGraph;
import com.model.gdf.GDFNode;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GDFGraphDrawer {

    private final List<GDFAttribute> nodes;
    private final List<DF> edges;
    private GDFGraph gdfGraph;

    public GDFGraphDrawer() {
        this.nodes = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    public void setGraph(GDFGraph gdfGraph) {
        this.gdfGraph = gdfGraph;
    }

    public void draw(Graphics2D graphics2D) {
        this.edges.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    public void addAttribute(GDFAttribute gdfAttribute) {
        GDFNode gdfNode = new GDFNode(gdfAttribute.getName());
        gdfGraph.addDfNodes(gdfNode);
        this.nodes.add(gdfAttribute);
        System.out.println(gdfGraph);
    }

    public void remove(GDFAttribute gdfAttribute) {
        if (gdfAttribute == null)
            return;
        gdfGraph.remove(gdfGraph.contains(gdfAttribute.getName()));
        this.nodes.remove(gdfAttribute);
        this.removeAttachedLinks(gdfAttribute);
        System.out.println(gdfGraph);
    }

    protected void removeAttachedLinks(GDFAttribute gdfAttribute) {
        this.edges.removeIf(e -> e.getNodeA().equals(gdfAttribute) || e.getNodeB().equals(gdfAttribute) );
    }

    public void rename(GDFAttribute gdfAttribute, String newName) {
        if (gdfAttribute == null)
            return;
        gdfGraph.setName(gdfGraph.contains(gdfAttribute.getName()), newName);
        gdfAttribute.setName(newName);
        System.out.println(gdfGraph);
    }

    public GDFAttribute contains(int x, int y) {
        return this.nodes.stream().filter(node -> node.contains(x, y))
                .findAny()
                .orElse(null);
    }

    public void addLink(GDFAttribute source, GDFAttribute target) {
        GDFNode gdfNodeSource =  gdfGraph.contains(source.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(target.getName());

        gdfNodeSource.addTarget(gdfNodeTarget.getName());
        DF df = new DF(source, target);

        this.edges.add(df);

        System.out.println(gdfGraph);
    }

    public void removeLink(DF df) {
        GraphicalNode sourceNode = df.getNodeA();
        GraphicalNode targetNode  = df.getNodeB();
        GDFNode gdfNodeSource =  gdfGraph.contains(sourceNode.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(targetNode.getName());

        gdfNodeSource.removeTarget(gdfNodeTarget.getName());
        this.edges.remove(df);

        System.out.println(gdfGraph);
    }

    public DF containsLink(int x, int y) {
        return this.edges.stream().filter(edge -> edge.contains(x, y))
                .findAny()
                .orElse(null);
    }
}
