package com.models.mcd;

import com.models.EntityObject;
import com.models.Property;

/**
 * 
 */
public class Entity extends EntityObject {

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
        if (!propertyList.isEmpty())
            return propertyList.get(0);
        return null;
    }

}
