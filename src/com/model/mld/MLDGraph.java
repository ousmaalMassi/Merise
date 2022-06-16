package com.model.mld;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MLDGraph {
    /**
     *
     */
    private List<MLDTable> tables;

    /**
     * Default constructor
     */
    public MLDGraph() {
        tables = new ArrayList<>();
    }

    public List<MLDTable> getTables() {
        return tables;
    }

    public void setTables(List<MLDTable> tables) {
        this.tables = tables;
    }

    /**
     *
     */
    public void associate(MLDTable table1, MLDTable table2) {
        //table1.addLink(table2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.tables.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public MLDTable getTable(String name){
        return this.tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
