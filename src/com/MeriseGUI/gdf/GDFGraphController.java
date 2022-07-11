package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphController;
import com.MeriseGUI.ddd.DDPanel;
import com.graphics.GLink;
import com.graphics.GNode;
import com.graphics.gdf.GComposedDF;
import com.graphics.gdf.GDFAttribute;
import com.graphics.gdf.GNodeGDF;
import com.graphics.gdf.GSimpleDF;
import com.models.gdf.ComposedNode;
import com.models.gdf.GDFGraph;
import com.models.gdf.GDFNode;

import java.awt.*;

public class GDFGraphController extends GraphController<GNodeGDF, GLink> {
    private GDFGraph gdfGraph;

    public GDFGraphController() {
    }

    public GDFGraph getGraph() {
        return gdfGraph;
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
    public void remove(GNodeGDF gNodeGDF) {
        if (gNodeGDF == null)
            return;
        DDPanel.setUsedInGDF(gNodeGDF.getName(), false);
        gdfGraph.remove(gdfGraph.contains(gNodeGDF.getName()));
        this.nodes.remove(gNodeGDF);
        this.removeAttachedLinks(gNodeGDF);
        System.out.println(gdfGraph);
    }

    private void removeAttachedLinks(GNodeGDF node) {
        this.links.removeIf(e -> e.getNodeA().equals(node) || e.getNodeB().equals(node) );
    }

    @Override
    public void rename(GNodeGDF gNodeGDF, String newName) {
        if (gNodeGDF == null)
            return;
        gdfGraph.setName(gdfGraph.contains(gNodeGDF.getName()), newName);
        gNodeGDF.setName(newName);
        System.out.println(gdfGraph);
    }

    @Override
    public GNodeGDF contains(int x, int y) {
        int lastIndex = this.nodes.size() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            GNodeGDF graphicalNode = nodes.get(i);
            if (graphicalNode.contains(x, y)) {
                nodes.remove(graphicalNode);
                nodes.add(graphicalNode);
                return graphicalNode;
            }
        }
        return null;
    }

    @Override
    public void addLink(GNodeGDF source, GNodeGDF target) {
        GDFNode gdfNodeSource =  gdfGraph.contains(source.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(target.getName());

        gdfNodeSource.addTarget(gdfNodeTarget.getName());
        GSimpleDF GSimpleDf = new GSimpleDF(source, target);

        this.links.add(GSimpleDf);

        System.out.println(gdfGraph);
    }

//    public void addGComposedDF(GDFAttribute gdfAttribute1, GDFAttribute gdfAttribute2) {
////        GDFNode gdfNodeGdfAttribute1 =  gdfGraph.contains(gdfAttribute1.getName());
////        GDFNode gdfNodeGdfAttribute2 =  gdfGraph.contains(gdfAttribute2.getName());
//
////        gdfNodeGdfAttribute1.addTarget(gdfNodeGdfAttribute2.getName());
//        GComposedDF gComposedDF = new GComposedDF(gdfAttribute1, gdfAttribute2);
//
//        this.links.add(gComposedDF);
//
//        System.out.println(gdfGraph);
//    }

    public void addComposedTrivialDF(GNodeGDF gNodeGDF1, GNodeGDF gNodeGDF2) {

        if (gNodeGDF1 instanceof GComposedDF) {
            ComposedNode gdfNode = (ComposedNode) gdfGraph.contains(gNodeGDF1.getName());
            this.links.add(new GLink(gNodeGDF1, gNodeGDF2));
            gdfNode.addSource(gNodeGDF2.getName());
        } else if (gNodeGDF2 instanceof GComposedDF) {
            ComposedNode gdfNode = (ComposedNode) gdfGraph.contains(gNodeGDF2.getName());
            this.links.add(new GLink(gNodeGDF1, gNodeGDF2));
            gdfNode.addSource(gNodeGDF1.getName());
        } else {

            int x = Math.min(gNodeGDF1.getX(),gNodeGDF2.getX()) + Math.abs(gNodeGDF1.getX()-gNodeGDF2.getX()) / 2;
            int y = Math.min(gNodeGDF1.getY(),gNodeGDF2.getY()) + Math.abs(gNodeGDF1.getY()-gNodeGDF2.getY()) / 2;
            GComposedDF node = new GComposedDF(x, y, "COMP");

            ComposedNode gdfNode = new ComposedNode(node.getName());

            gdfGraph.addDfNodes(gdfNode);
            this.nodes.add(node);
            GLink gLink1 = new GLink(node, gNodeGDF1);
            gdfNode.addSource(gNodeGDF1.getName());
            this.links.add(gLink1);
            GLink gLink2 = new GLink(node, gNodeGDF2);
            this.links.add(gLink2);
            gdfNode.addSource(gNodeGDF2.getName());
        }
        System.out.println(gdfGraph);
    }

    @Override
    public void addNode(GNodeGDF node) {
        GDFNode gdfNode = new GDFNode(node.getName());
        gdfGraph.addDfNodes(gdfNode);
        this.nodes.add(node);
        System.out.println(gdfGraph);
    }

    @Override
    public void removeLink(GLink link) {
        GNode sourceNode = link.getNodeA();
        GNode targetNode  = link.getNodeB();
        GDFNode gdfNodeSource =  gdfGraph.contains(sourceNode.getName());
        GDFNode gdfNodeTarget =  gdfGraph.contains(targetNode.getName());

        gdfNodeSource.removeTarget(gdfNodeTarget.getName());
        this.links.remove(link);

        System.out.println(gdfGraph);
    }
}
