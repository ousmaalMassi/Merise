package com.mld;

import com.Node;
import com.mcd.Property;

import java.util.List;

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
        List<Property> propertyList = this.getPropertyList();
        StringBuilder propertiesName = new StringBuilder();
        propertyList.forEach(property ->
                        propertiesName.append(property.getName()).append(", ")
                );
        return this.name+" : "+propertiesName+"\n";
    }
}
