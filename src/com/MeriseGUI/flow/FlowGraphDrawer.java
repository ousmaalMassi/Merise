package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;
import com.MeriseGUI.mcd.AssociationView;
import com.MeriseGUI.mcd.EntityView;
import com.MeriseGUI.mcd.GraphicalMCDNode;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class FlowGraphDrawer {

    private final List<GraphicalNode> nodes;
    private final List<Flow> edges;

    public FlowGraphDrawer() {
        this.nodes = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    public void draw(Graphics2D graphics2D) {

        this.nodes.forEach(node -> {
            if (node instanceof Domain)
                node.draw(graphics2D);
        });

        this.edges.forEach(edge -> edge.draw(graphics2D));

        this.nodes.forEach(node -> {
            if (node instanceof Actor)
                node.draw(graphics2D);
        });
    }

    public void remove(GraphicalNode actor) {
        if (actor == null)
            return;
        this.nodes.remove(actor);
    }

    public void rename(GraphicalNode actor) {
        if (actor == null)
            return;
        System.out.println("rename");
    }

    public void addLink(Actor actor1, Actor actor2) {
        Flow flow = new Flow(actor1, actor2);
        this.edges.add(flow);
    }

    public void addInternalActor(InternalActor internalActor) {
        this.nodes.add(internalActor);
    }

    public void addExternalActor(ExternalActor externalActor) {
        this.nodes.add(externalActor);
    }

    public void addDomain(Domain domain) {
        this.nodes.add(0, domain);
    }

    public GraphicalNode contains(int x, int y) {
        return this.nodes.stream().filter(node -> node.contains(x, y))
                .findAny()
                .orElse(null);
    }

    public void removeLink(Flow linkUnderCursor) {
        GraphicalNode nodeA = linkUnderCursor.getNodeA();
        GraphicalNode nodeB = linkUnderCursor.getNodeB();


    }
}
