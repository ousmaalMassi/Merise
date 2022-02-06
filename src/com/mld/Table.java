package com.mld;

import com.mcd.Node;

import java.util.List;

public class Table extends Node {
    List<Table> linksList;

    public Table(String name) {
        super(name);
    }

    public void addLink(Table table){
        this.linksList.add(table);
    }
}
