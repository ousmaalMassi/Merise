package com.mcd;

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
     * @return list of the graph nodes
     */
    public List<Node> getNodeList() {
        return nodeList;
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
    public void removeNode(Node node) {
        this.nodeList.remove(node);
    }

    /**
     *
     */
    public void associate(Association association, Entity entity) {
        association.addParty(entity.name, Cardinalities.DEFAULT_CARDINALITY);
    }

    @Override
    public String toString(){
        return String.format("""
                    {
                        "nodeList" : %s,
                    }
                """, this.nodeList);
    }

    public Node search(String string){
        return this.nodeList.stream()
                .filter(node -> string.equals(node.getName()))
                .findAny()
                .orElse(null);
    }
}
