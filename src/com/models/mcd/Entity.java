package com.models.mcd;

import com.models.MeriseObject;
import com.models.Property;

/**
 * 
 */
public class Entity extends MeriseObject {

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
//        for (Property property : this.getPropertyList())
//            if (property.getConstraints().contains(Property.Constraints.PRIMARY_KEY))
//                return property;
        return null;
    }

}
