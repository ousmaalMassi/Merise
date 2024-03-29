package com.models;

import com.exceptions.DuplicateProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EntityObject extends MeriseObject {

    /**
     *
     */
    protected List<Property> propertyList;

    /**
     * constructor
     */
    public EntityObject(String name) {
        this.name = name;
        this.propertyList = new ArrayList<>();
    }

    /**
     *
     */
    public void addProperty(Property property) {
        try {
            this.normalize(property);
            this.propertyList.add(property);
        } catch (DuplicateProperty e) {
            e.printStackTrace();
        }
    }

    private void normalize(Property property) throws DuplicateProperty {
        String code = property.getCode();
        if (this.search(code) == null) {
            property.setCode(code);
        } else {
            throw new DuplicateProperty("Duplicate Property Name: '" + code + "' Property");
        }
    }

    public Property search(String string) {
        return this.propertyList.stream()
                .filter(property -> property.getName().equals(string))
                .findAny()
                .orElse(null);
    }

    /**
     *
     */
    public void removeProperty(Property property) {
        this.propertyList.remove(property);
    }

    /**
     *
     */
    public List<Property> getPropertyList() {
        return propertyList;
    }

    /**
     *
     */
    public void setPropertyList(List<Property> propertyList) {
        propertyList.forEach(this::addProperty);
    }

    /*@Override
    public String toString(){
        return String.format("""
                    {
                        "name" : "%s",
                        "propertyList" : %s
                    }
                """, this.name,  this.propertyList);
    }*/

    @Override
    public String toString() {
        return"""
                  
                  |____ name : %s,
                  |____ propertyList : %s""".formatted(this.name, this.propertyList);
    }
}

