package com.mld;

import com.Node;

public class Table extends Node {
    public Table(String name) {
        super(name);
    }
    @Override
    public String toString(){
        return this.name+" "+getPropertyList();
    }
}
