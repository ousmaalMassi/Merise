package com.MeriseGUI;

import com.exceptions.DuplicateMeriseObject;
import com.graphics.GLink;
import com.graphics.GNode;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GraphController<N extends GNode, L extends GLink> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected List<N> nodes;
    protected List<L> links;

    public GraphController() {
        nodes = new LinkedList<>();
        links = new LinkedList<>();
    }

    public abstract void printGraph(Graphics2D graphics2D);

    public abstract void removeNode(N node);

    public abstract void rename(N node, String newName);

    public abstract void addLink(N node1, N node2);

    public abstract void addNode(N node) throws DuplicateMeriseObject;

    public abstract void removeLink(L link);

    public N containsNode(int x, int y) {
        int lastIndex = this.nodes.size() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            N graphicalNode = nodes.get(i);
            if (graphicalNode.contains(x, y)) {
                nodes.remove(graphicalNode);
                nodes.add(graphicalNode);
                return graphicalNode;
            }
        }
        return null;
    }

    public L containsLink(int x, int y) {
        return this.links.stream().filter(link -> link.contains(x, y))
                .findAny()
                .orElse(null);
    }


    public List<N> getNodes() {
        return this.nodes;
    }

    public List<L> getLinks() {
        return this.links;
    }

    public void setNodes(List<N> flowNodes) {
        this.nodes = flowNodes;
    }

    public void setLinks(List<L> flowLinks) {
        this.links = flowLinks;
    }
}
