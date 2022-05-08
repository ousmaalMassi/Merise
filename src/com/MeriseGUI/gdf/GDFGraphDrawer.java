package com.MeriseGUI.gdf;

import com.MeriseGUI.mcd.AssociationView;
import com.MeriseGUI.mcd.EntityView;
import com.exception.DuplicateMeriseObject;
import com.gdf.GDFGraph;
import com.gdf.GDFNode;
import com.mcd.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GDFGraphDrawer {

    private final List<GDFAttribute> nodes;
    private final List<GDFLink> edges;
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
        int attributeNo = gdfGraph.getDfNodes().size();
        String attributeName = "attr "+attributeNo;
        GDFNode gdfNode = new GDFNode(attributeName);
        gdfAttribute.setName(attributeName);
            gdfGraph.addDfNodes(gdfNode);
            this.nodes.add(gdfAttribute);
        System.out.println(gdfGraph);
    }

    public void remove(GDFAttribute gdfAttribute) {
        if (gdfAttribute == null)
            return;
        gdfGraph.remove(gdfGraph.contains(gdfAttribute.getName()));
        this.nodes.remove(gdfAttribute);
        System.out.println(gdfGraph);
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

    public void addLink(GDFAttribute gdfAttribute1, GDFAttribute gdfAttribute2) {
        GDFNode gdfNode1 =  gdfGraph.contains(gdfAttribute1.getName());
        GDFNode gdfNode2 =  gdfGraph.contains(gdfAttribute2.getName());

        gdfNode1.addTarget(gdfNode2.getName());
        GDFLink graphicalLink = new GDFLink(gdfAttribute1, gdfAttribute2);

        this.edges.add(graphicalLink);

        System.out.println(gdfGraph);
    }

}
