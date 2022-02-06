package com.mcd;

import java.util.*;

/**
 *
 */
public class MCDGraph {

    /**
     *
     */
    private final List<Node> nodeList;


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
     * @param node to add in the list
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
     * @param association, and entity to associate with
     * @param entity
     */
    public void associate(Association association, Entity entity) {
        association.addLinks(entity.name, Cardinalities.DEFAULT_CARDINALITY);
    }


    /**
     * @param string
     * @return entity
     */
    public Entity search(String string){
        return (Entity) this.nodeList.stream()
                .filter(node -> node.getName().equals(string))
                .findAny()
                .orElse(null);
    }


    @Override
    public String toString(){
        return String.format("""
                    {
                        "nodeList" : %s,
                    }
                """, this.nodeList);
    }

}
