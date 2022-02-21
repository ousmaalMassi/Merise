package com.mcd;

import com.Node;

/**
 * 
 */
public class Entity extends Node {

    /**
     * @param name of the entity
     */
    public Entity(String name) {
        super(name);
    }

    /**
     * @return property
     */
    public Property getId(){
        for (Property property : this.getPropertyList())
            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
                return property;
        return null;
    }

}