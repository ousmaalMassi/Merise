package com.mld;

import com.MeriseObject;
import com.mcd.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLDTable extends MeriseObject {
    private Map<Property, MLDTable> foreignKeys;

    public MLDTable(String name) {
        super(name);
        this.foreignKeys = new HashMap<>();
    }

    public Property getPrimaryKey(){
        return propertyList.stream().filter(p -> p.constraints.contains(Property.Constraints.PRIMARY_KEY)).findAny().orElse(null);
    }

    public void setPrimaryKey(String propertyCode){
        Property property = propertyList.stream().filter(p -> p.code.equals(propertyCode)).findAny().orElse(null);
        property.constraints.add(Property.Constraints.PRIMARY_KEY);
    }

    public void setForeignKey(MLDTable refTable){
        Property property = propertyList.stream().filter(p -> p.code.equals(refTable.getPrimaryKey().code)).findAny().orElse(null);
        if (property == null) {
            property = refTable.getPrimaryKey();
            propertyList.add(property);
        }
        property.constraints.add(Property.Constraints.FOREIGN_KEY);
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
