package com.MeriseGUI.gdf;

import com.MeriseGUI.GraphController;
import com.MeriseGUI.ddd.DDPanel;
import com.NamesGenerator;
import com.graphics.GLink;
import com.graphics.GNode;
import com.graphics.gdf.*;
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
    public void printGraph(Graphics2D graphics2D) {
        this.links.forEach(edge -> edge.draw(graphics2D));
        this.nodes.forEach(node -> node.draw(graphics2D));
    }

    @Override
    public void addNode(GNodeGDF node) {
        GDFNode gdfNode = new GDFNode(node.getName());
        gdfGraph.addDfNode(gdfNode);
        this.nodes.add(node);
        System.out.println(gdfGraph);
    }

    @Override
    public void removeNode(GNodeGDF gNodeGDF) {
        if (gNodeGDF == null)
            return;
        DDPanel.setUsedInGDF(gNodeGDF.getName(), false);
        gdfGraph.removeDfNode(gdfGraph.containsAttribute(gNodeGDF.getName()));
        this.nodes.remove(gNodeGDF);
        this.removeAttachedLinks(gNodeGDF);
        System.out.println(gdfGraph);
    }

    private void removeAttachedLinks(GNodeGDF node) {
        this.links.removeIf(l -> l.getNodeA().equals(node) || l.getNodeB().equals(node));
//         this.gdfGraph.getDfNodes()
//                .removeIf(n -> n instanceof ComposedNode composedNode
//                        && composedNode.getSources().contains(node.getName())
//                        && composedNode.getSources().size() < 2);
    }

    @Override
    public void addLink(GNodeGDF source, GNodeGDF target) {
        GDFNode gdfNodeSource;
        GDFNode gdfNodeTarget;

        if (source instanceof GDFAttribute)
            gdfNodeSource = gdfGraph.containsAttribute(source.getName());
        else
            gdfNodeSource = gdfGraph.containsComposedNode(source.getName());

        try {
            gdfNodeTarget = gdfGraph.containsAttribute(target.getName());
        } catch (Exception e) {
            return;
        }

        System.out.println(gdfNodeSource);
        gdfNodeSource.addTarget(gdfNodeTarget.getName());
        GSimpleDF GSimpleDf = new GSimpleDF(source, target);

        this.links.add(GSimpleDf);

        System.out.println(gdfGraph);
    }

    @Override
    public void removeLink(GLink link) {
        GNode sourceNode = link.getNodeA();
        GNode targetNode = link.getNodeB();
        GDFNode gdfNodeSource = gdfGraph.containsAttribute(sourceNode.getName());
        GDFNode gdfNodeTarget = gdfGraph.containsAttribute(targetNode.getName());

        gdfNodeSource.removeTarget(gdfNodeTarget.getName());
        this.links.remove(link);

        System.out.println(gdfGraph);
    }

    @Override
    public void rename(GNodeGDF gNodeGDF, String newName) {
        if (gNodeGDF == null)
            return;
        gdfGraph.setName(gdfGraph.containsAttribute(gNodeGDF.getName()), newName);
        gNodeGDF.setName(newName);
        System.out.println(gdfGraph);
    }

    @Override
    public GNodeGDF containsNode(int x, int y) {
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

    public GNodeGDF addComposedDF(GNodeGDF gNodeGDF1, GNodeGDF gNodeGDF2, DFType type) {

        int x = Math.min(gNodeGDF1.getX(), gNodeGDF2.getX()) + Math.abs(gNodeGDF1.getX() - gNodeGDF2.getX()) / 2;
        int y = Math.min(gNodeGDF1.getY(), gNodeGDF2.getY()) + Math.abs(gNodeGDF1.getY() - gNodeGDF2.getY()) / 2;

        ComposedNode composedNode = new ComposedNode(NamesGenerator.generateName(gdfGraph.getComposedNodes(), type.toString()));
        GNodeGDF gNodeGDF = new GComposedDF(x, y, composedNode.getName(), type);

        gdfGraph.addComposedNode(composedNode);
        this.nodes.add(gNodeGDF);

        GLink gLink1 = new GLink(gNodeGDF, gNodeGDF1);
        GLink gLink2 = new GLink(gNodeGDF, gNodeGDF2);

        composedNode.addSource(gNodeGDF1.getName());
        composedNode.addSource(gNodeGDF2.getName());

        this.links.add(gLink1);
        this.links.add(gLink2);

        System.out.println(gdfGraph);
        return gNodeGDF;
    }
}
