package com;

import com.mcd.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Node {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected List<Property> propertyList;

    /**
     * constructor
     */
    public Node(String name) {
        this.name = name;
        this.propertyList = new ArrayList<>();
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public void addProperty(Property property) {
        this.propertyList.add(property);
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
        this.propertyList = propertyList;
    }

    @Override
    public String toString(){
        return String.format("""
                    {
                        "name" : "%s",
                        "propertyList" : %s
                    }
                """, this.name,  this.propertyList);
    }
}
