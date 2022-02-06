package com.mld;

import com.mcd.Property;

import java.util.*;

/**
 *
 */
public class MLDGraph {

    /**
     *
     */
    private final List<Table> nodeList;

    /**
     *
     */
    private Map<String, Property> foreignKeys;

    /**
     * Default constructor
     */
    public MLDGraph() {
        this.nodeList = new ArrayList<>();
        foreignKeys = new HashMap<>();
    }


    /**
     * @return list of the graph nodes
     */
    public List<Table> getNodeList() {
        return nodeList;
    }


    /**
     *
     */
    public void addNode(Table node) {
        this.nodeList.add(node);
    }

    /**
     *
     */
    public void removeNode(Table node) {
        this.nodeList.remove(node);
    }

    public Map<String, Property> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Map<String, Property> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    /**
     *
     */
    public void associate(Table table1, Table table2) {
        table1.addLink(table2);
    }

    @Override
    public String toString(){
        return String.format("""
                    {
                        "nodeList" : %s,
                    }
                """, this.nodeList);
    }

    public Table search(String string){
        return this.nodeList.stream()
                .filter(node -> node.getName().equals(string))
                .findAny()
                .orElse(null);
    }
}
