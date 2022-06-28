package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphController;
import com.MeriseGUI.GraphicalNode;
import com.model.gdf.GDFGraph;
import com.model.gdf.GDFNode;

import java.awt.*;

public class GDFGraphController extends GraphController<GDFAttribute, DF> {
    private GDFGraph gdfGraph;

    public GDFGraphController() {
    }

    public void setGraph(GDFGraph gdfGraph) {
        this.gdfGraph = gdfGraph;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        this.links.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void remove(GDFAttribute gdfAttribute) {
        if (gdfAttribute == null)
            return;
        gdfGraph.remove(gdfGraph.contains(gdfAttribute.getName()));
        this.nodes.remove(gdfAttribute);
        this.removeAttachedLinks(gdfAttribute);
        System.out.println(gdfGraph);
    }

    private void removeAttachedLinks(GDFAttribute node) {
        this.links.removeIf(e -> e.getNodeA().equals(node) || e.getNodeB().equals(node) );
    }

    @Override
    public void rename(GDFAttribute gdfAttribute, String newName) {
        if (gdfAttribute == null)
            return;
        gdfGraph.setName(gdfGraph.contains(gdfAttribute.getName()), newName);
        gdfAttribute.setName(newName);
        System.out.println(gdfGraph);
    }

    @Override
    public GDFAttribute contains(int x, int y) {
        int lastIndex = this.nodes.size() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            GDFAttribute graphicalNode = nodes.get(i);
            if (graphicalNode.contains(x, y)) {
                nodes.remove(graphicalNode);
                nodes.add(graphicalNode);
                return graphicalNode;
            }
        }
        return null;
    }

    @Override
    public void addLink(GDFAttribute source, GDFAttribute target) {
        GDFNode gdfNodeSource =  gdfGraph.contains(source.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(target.getName());

        gdfNodeSource.addTarget(gdfNodeTarget.getName());
        DF df = new DF(source, target);

        this.links.add(df);

        System.out.println(gdfGraph);
    }

    @Override
    public void addNode(GDFAttribute node) {
        GDFNode gdfNode = new GDFNode(node.getName());
        gdfGraph.addDfNodes(gdfNode);
        this.nodes.add(node);
        System.out.println(gdfGraph);
    }

    @Override
    public void removeLink(DF link) {
        GraphicalNode sourceNode = link.getNodeA();
        GraphicalNode targetNode  = link.getNodeB();
        GDFNode gdfNodeSource =  gdfGraph.contains(sourceNode.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(targetNode.getName());

        gdfNodeSource.removeTarget(gdfNodeTarget.getName());
        this.links.remove(link);

        System.out.println(gdfGraph);
    }
}
