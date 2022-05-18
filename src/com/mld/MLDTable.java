package com.mld;

import com.MeriseObject;
import com.mcd.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLDTable extends MeriseObject {
    List<String> primaryKeys;
    Map<String, MLDTable> foreignKeys;

    public MLDTable(String name) {
        super(name);
        this.primaryKeys = new ArrayList<>();
        this.foreignKeys = new HashMap<>();
    }

    /*public Property getPrimaryKey(){
        for (Property property : this.getPropertyList())
            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
                return property;
        return null;
    }*/

    public Property getPrimaryKey(){
        return this.propertyList.get(0);
    }

    public void addPrimaryKey(Property property){
        if (!primaryKeys.contains(property))
            primaryKeys.add(property.getCode());
    }

    public void removePrimaryKey(Property property){
        primaryKeys.remove(property.getCode());
    }

    public void addForeignKey(MLDTable table){
        Property property = table.getPrimaryKey();
        if (!foreignKeys.containsKey(property.getCode())) {
            this.addProperty(property);
            foreignKeys.put(property.getCode(), table);
        }
    }

    public void removeForeignKey(String propertyName){
        foreignKeys.remove(propertyName);
    }

    public Map<String, MLDTable> getForeignKeys() {
        return foreignKeys;
    }

    @Override
    public String toString(){
        List<Property> propertyList = this.getPropertyList();
        List<String> list = new ArrayList<>();
        propertyList.forEach(property -> list.add(property.getCode()));
        return this.name+" : "+String.join(", ", list)+"\n";
    }
}