package com.mpd;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class MPDGraph {
    /**
     *
     */
    private Map<Table, Map<String, String>> tables;

    /**
     * Default constructor
     */
    public MPDGraph() {
        tables = new LinkedHashMap<>();
    }


    public Map<Table, Map<String, String>>getTables() {
        return tables;
    }

    public void setTables(Map<Table, Map<String, String>>tables) {
        this.tables = tables;
    }

    /**
     *
     */
    /*public void associate(GraphicalMPDTable table1, GraphicalMPDTable table2) {
        table1.addLink(table2);
    }*/

    @Override
    public String toString(){
        return String.format("""
                    {
                        "tables" : %s,
                    }
                """, this.tables);
    }


    public String toJSON(){
        return String.format("""
                    {
                        "tables" : %s,
                    }
                """, this.tables);
    }

    /*public GraphicalMPDTable search(String string){
        return this.nodeList.stream()
                .filter(node -> node.getName().equals(string))
                .findAny()
                .orElse(null);
    }*/
}
