package com.model.mld;

import com.model.MeriseObject;
import com.model.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLDTable extends MeriseObject {
    private Map<Property, MLDTable> foreignKeys;
    private List<Property> primaryKeys;

    public MLDTable(String name) {
        super(name);
        this.foreignKeys = new HashMap<>();
        this.primaryKeys = new ArrayList<>();
    }

    public List<Property> getPrimaryAllKeys() {
        return primaryKeys;
    }

    public Property getPrimaryKey(){
        return primaryKeys.get(0);
    }

    public void setPrimaryKey(String propertyCode){
        Property property = propertyList.stream().filter(p -> p.code.equals(propertyCode)).findAny().orElse(null);
        primaryKeys.add(property);
    }

    public void setForeignKey(MLDTable refTable){
        Property property = propertyList.stream().filter(p -> p.code.equals(refTable.getPrimaryKey().code)).findAny().orElse(null);
        if (property == null) {
            property = refTable.getPrimaryKey();
            propertyList.add(property);
        }
        foreignKeys.put(property, refTable);
    }

    public Map<Property, MLDTable> getForeignKeys() {
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
