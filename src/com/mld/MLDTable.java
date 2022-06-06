package com.mld;

import com.MeriseObject;
import com.mcd.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLDTable extends MeriseObject {

    List<Property> primaryKeys;

    Map<Property, MLDTable> foreignKeys;

    public MLDTable(String name) {
        super(name);
        this.primaryKeys = new ArrayList<>();
        this.foreignKeys = new HashMap<>();
    }

    public List<Property> getPrimaryKeys(){
        return this.primaryKeys;
    }

    public void addPrimaryKey(Property property){
        if (!primaryKeys.contains(property))
            primaryKeys.add(property);
    }

    public void removePrimaryKey(Property property){
        primaryKeys.remove(property);
    }

    public void addForeignKey(MLDTable table){
        table.getPrimaryKeys().forEach(property -> {
            if (!foreignKeys.containsKey(property)) {
                foreignKeys.put(property, table);
            }
        });
    }

    public void removeForeignKey(String propertyName){
        foreignKeys.remove(propertyName);
    }

    public Map<Property, MLDTable> getForeignKeys() {
        return foreignKeys;
    }

    @Override
    public String toString(){
        List<Property> propertyList = this.getPropertyList();
        List<String> list = new ArrayList<>();
        propertyList.forEach(property -> list.add(property.getCode()));
        foreignKeys.forEach((property, mldTable) -> list.add(property.getCode()));
        return this.name+" : "+String.join(", ", list)+"\n";
    }
}
