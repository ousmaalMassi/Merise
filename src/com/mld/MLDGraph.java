package com.mld;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> foreignKeys;

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

    public Map<String, String> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Map<String, String> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    /**
     *

    public void associate(Table table1, Table table2) {
        table1.addLink(table2);
    }*/

    @Override
    public String toString(){
        return String.format("""
                    {
                        "nodeList" : %s,
                        "foreignKeys" : %s,
                    }
                """, this.nodeList, this.foreignKeys);
    }


    public String toJSON(){
        return String.format("""
                    {
                        "nodeList" : %s,
                        "foreignKeys" : %s,
                    }
                """, this.nodeList, this.foreignKeys);
    }

    public Table search(String string){
        return this.nodeList.stream()
                .filter(node -> node.getName().equals(string))
                .findAny()
                .orElse(null);
    }
}
