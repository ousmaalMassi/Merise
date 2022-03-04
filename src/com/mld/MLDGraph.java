package com.mld;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MLDGraph {
    /**
     *
     */
    private List<Table> tables;

    /**
     * Default constructor
     */
    public MLDGraph() {
        tables = new ArrayList<>();
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     *
     */
    public void associate(Table table1, Table table2) {
        //table1.addLink(table2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.tables.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public String toJSON() {
        return String.format("""
                    {
                        "tables" : %s,
                    }
                """, this.tables);
    }

    public Table search(String name){
        return this.tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
