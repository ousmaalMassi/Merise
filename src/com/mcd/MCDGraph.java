package com.mcd;

import com.mcd.Node;

import java.util.*;

/**
 * 
 */
public class MCDGraph {

    /**
     * Default constructor
     */
    public MCDGraph() {
        this.nodeList = new ArrayList<>();
    }

    /**
     *
     */
    private final List<Node> nodeList;

    /**
     * 
     */
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    /**
     * 
     */
    public void delNode(Node node) {
        this.nodeList.remove(node);
    }

    /**
     * 
     */
    public void associate() {
        // TODO implement here
    }

}
