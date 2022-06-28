package com.MeriseGUI.flow;

import com.MeriseGUI.GArrow;
import com.MeriseGUI.GraphController;
import com.MeriseGUI.GraphicalNode;

import java.awt.*;

public class FlowGraphController extends GraphController<GraphicalNode, GArrow> {

    public FlowGraphController() {
    }

    @Override
    public void draw(Graphics2D graphics2D) {

        this.nodes.forEach(node -> {
            if (node instanceof Domain)
                node.draw(graphics2D);
        });

        this.links.forEach(link -> link.draw(graphics2D));

        this.nodes.forEach(node -> {
            if (node instanceof Actor)
                node.draw(graphics2D);
        });
    }

    @Override
    public void remove(GraphicalNode graphicalNode) {
        if (graphicalNode == null)
            return;
        this.nodes.remove(graphicalNode);
    }

    @Override
    public void rename(GraphicalNode graphicalNode, String newName) {
        if (graphicalNode == null)
            return;
        System.out.println(newName);
        graphicalNode.setName(newName);
    }

    @Override
    public void addLink(GraphicalNode t1, GraphicalNode t2) {
        // TODO verify duplicated flows
        GArrow flow = new GArrow(t1, t2);
        this.links.add(flow);
    }

    @Override
    public void addNode(GraphicalNode graphicalNode) {
        switch (graphicalNode){
            case InternalActor internalActor -> addInternalActor( internalActor);
            case ExternalActor externalActor -> addExternalActor( externalActor);
            case Domain domain -> addDomain(domain);
            default -> throw new IllegalStateException("Unexpected value: " + graphicalNode);
        }
    }

    private void addInternalActor(InternalActor internalActor) {
        this.nodes.add(internalActor);
    }

    private void addExternalActor(ExternalActor externalActor) {
        this.nodes.add(externalActor);
    }

    private void addDomain(Domain domain) {
        this.nodes.add(0, domain);
    }

    @Override
    public void removeLink(GArrow graphicalLink) {
        this.links.remove(graphicalLink);
    }
}
