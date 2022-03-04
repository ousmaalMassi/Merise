package com.mld;

import com.Node;
import com.mcd.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table extends Node {
    List<Property> primaryKeys;
    Map<String, Table> foreignKeys;

    public Table(String name) {
        super(name);
        this.primaryKeys = new ArrayList<>();
        this.foreignKeys = new HashMap<>();
    }

    public Property getPrimaryKey(){
        for (Property property : this.getPropertyList())
            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
                return property;
        return null;
    }

    public void addPrimaryKey(Property property){
        if (!primaryKeys.contains(property))
            primaryKeys.add(property);
    }

    public void removePrimaryKey(Property property){
        primaryKeys.remove(property);
    }

    public void addForeignKey(Table table){
        Property property = table.getPrimaryKey();
        if (!foreignKeys.containsKey(property.getName())) {
            this.addProperty(property);
            foreignKeys.put(property.getName(), table);
        }
    }

    public void removeForeignKey(String propertyName){
        foreignKeys.remove(propertyName);
    }

    public Map<String, Table> getForeignKeys() {
        return foreignKeys;
    }

    @Override
    public String toString(){
        List<Property> propertyList = this.getPropertyList();
        List<String> list = new ArrayList<>();
        propertyList.forEach(property -> list.add(property.getName()));
        return this.name+" : "+String.join(", ", list)+"\n";
    }
}
