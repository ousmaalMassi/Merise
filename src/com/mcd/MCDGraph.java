package com.mcd;

import com.Node;

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
     * @param association that associate a list of entities
     * @param entity to associate with
     */
    public void link(Association association, Entity entity) {
        this.nodeList.remove(entity);
        association.addLink(entity, Cardinalities.DEFAULT_CARDINALITY);
    }

    /**
     * @param association that associate a list of entities
     * @param entity to associate with
     */
    public void unlink(Association association, Entity entity) {
        this.nodeList.add(entity);
        association.removeLink(entity);
    }

    /**
     * @param name of the entity
     * @return entity
     */
    public Entity getEntityByName(String name){
        return (Entity) this.nodeList.stream()
                .filter(node -> node.getName().equals(name))
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
