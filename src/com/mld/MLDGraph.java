package com.mld;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class MLDGraph {
    /**
     *
     */
    private Map<Table, Map<String, String>> tables;

    /**
     * Default constructor
     */
    public MLDGraph() {
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
    /*public void associate(Table table1, Table table2) {
        table1.addLink(table2);
    }*/

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        this.tables.forEach((table, fk) ->
                stringBuilder.append(table)
                );
        return stringBuilder.toString();
    }


    public String toJSON(){
        return String.format("""
                    {
                        "tables" : %s,
                    }
                """, this.tables);
    }

    /*public Table search(String string){
        return this.nodeList.stream()
                .filter(node -> node.getName().equals(string))
                .findAny()
                .orElse(null);
    }*/
}
