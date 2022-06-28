package com.MeriseGUI;

import com.graphics.GraphicalLink;
import com.graphics.GraphicalNode;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GraphController<T extends GraphicalNode, V extends GraphicalLink>{

    protected final List<T> nodes;
    protected final List<V> links;

    public GraphController() {
        nodes = new LinkedList<>();
        links = new LinkedList<>();
    }

    public abstract void draw(Graphics2D graphics2D);

    public abstract void remove(T node);

    public abstract void rename(T node, String newName);

    public abstract void addLink(T node1, T node2);

    public abstract void addNode(T node);

    public abstract void removeLink(V link);

    public T contains(int x, int y) {
        int lastIndex = this.nodes.size() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            T graphicalNode = nodes.get(i);
            if (graphicalNode.contains(x, y)) {
                nodes.remove(graphicalNode);
                nodes.add(graphicalNode);
                return graphicalNode;
            }
        }
        return null;
    }

    public V containsLink(int x, int y) {
        return this.links.stream().filter(link -> link.contains(x, y))
                .findAny()
                .orElse(null);
    }
}
