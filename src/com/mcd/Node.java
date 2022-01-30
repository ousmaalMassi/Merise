package com.mcd;

import java.util.*;

/**
 * 
 */
public class Node {



    /**
     * Default constructor
     */
    public Node(String name) {
        this.name = name;
        this.propertyList = new ArrayList<>();
    }

    /**
     * 
     */
    protected String name;

    /**
     * 
     */
    protected List<Property> propertyList;


    /**
     * 
     */
    public void addProperty(Property property) {
        this.propertyList.add(property);
    }

    /**
     * 
     */
    public void delProperty(Property property) {
        this.propertyList.remove(property);
    }


    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }
}