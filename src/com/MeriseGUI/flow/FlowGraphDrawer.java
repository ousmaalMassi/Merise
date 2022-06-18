package com.MeriseGUI.flow;

import com.MeriseGUI.GraphicalNode;

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

    public void rename(GraphicalNode node, String newName) {
        if (node == null)
            return;
        System.out.println(newName);
        node.setName(newName);
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
        /*return this.nodes.stream().filter(node -> node.contains(x, y))
                .findAny()
                .orElse(null);*/
        for (int i = this.nodes.size() - 1; i >= 0; i--)
            if (nodes.get(i).contains(x, y))
                return nodes.get(i);
        return null;
    }

    public void removeLink(Flow linkUnderCursor) {
        this.edges.remove(linkUnderCursor);
    }

    public Flow containsLink(int x, int y) {
        return this.edges.stream().filter(edge -> edge.contains(x, y))
                .findAny()
                .orElse(null);
    }
}
