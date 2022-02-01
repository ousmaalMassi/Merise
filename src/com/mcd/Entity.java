package com.mcd;

/**
 * 
 */
public class Entity extends Node {

    /**
     * Default constructor
     */
    public Entity(String name) {
        super(name);
    }

    public Property getIdProperty(){
        for (Property property : this.getPropertyList())
            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
                return property;
        return null;
    }

}