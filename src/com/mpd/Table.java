package com.mpd;

import com.Node;
import com.mcd.Property;

public class Table extends Node {
    public Table(String name) {
        super(name);
    }

    public Property getPrimaryKey(){
        for (Property property : this.getPropertyList())
            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
                return property;
        return null;
    }

    @Override
    public String toString(){
        return this.name+" "+getPropertyList();
    }
}
