package com.mpd;


import com.mld.MLDTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MPDGraph {
    /**
     *
     */
    private List<MLDTable> tables;

    /**
     * Default constructor
     */
    public MPDGraph() {
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

    public String toJSON() {
        return String.format("""
                    {
                        "tables" : %s,
                    }
                """, this.tables);
    }

    public MLDTable search(String name){
        return this.tables
                .stream()
                .filter(table -> table.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
