package com.MeriseGUI.flow;

import com.MeriseGUI.GraphController;
import com.graphics.GArrow;
import com.graphics.GNode;
import com.graphics.flow.Actor;
import com.graphics.flow.Domain;

import java.awt.*;

public class FlowGraphController extends GraphController<GNode, GArrow> {

    public FlowGraphController() {
    }

    @Override
    public void printGraph(Graphics2D graphics2D) {

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
    public void removeNode(GNode gNode) {
        if (gNode == null)
            return;
        this.nodes.remove(gNode);
    }

    @Override
    public void rename(GNode gNode, String newName) {
        if (gNode == null)
            return;
        System.out.println(newName);
        gNode.setName(newName);
    }

    @Override
    public void addLink(GNode t1, GNode t2) {
        // TODO verify duplicated flows
        GArrow flow = new GArrow(t1, t2);
        this.links.add(flow);
    }

    @Override
    public void addNode(GNode gNode) {
        switch (gNode){
            case Actor actor -> addActor(actor);
            case Domain domain -> addDomain(domain);
            default -> throw new IllegalStateException("Unexpected value: " + gNode);
        }
    }

    private void addActor(Actor actor) {
        this.nodes.add(actor);
    }

    private void addDomain(Domain domain) {
        this.nodes.add(0, domain);
    }

    @Override
    public void removeLink(GArrow graphicalLink) {
        this.links.remove(graphicalLink);
    }
}
