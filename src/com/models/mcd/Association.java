package com.models.mcd;

import com.models.EntityObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Association extends EntityObject {

    private Map<Entity, Cardinality> links;

    /**
     * constructor
     */
    public Association(String name) {
        super(name);
        this.links = new HashMap<>();
    }

    /**
     * @return List of Links
     */
    public Map<Entity, Cardinality> getLinks() {
        return links;
    }

    /**
     * @param links between Entities and Cardinalities
     */
    public void setLinks(Map<Entity, Cardinality> links) {
        this.links = links;
    }

    public void setCard(Entity entity, Cardinality cardinality) {
        this.links.replace(entity, cardinality);
    }

    @Override
    public String toString(){
        return """
                  
                  |____ name : %s,
                  |____ propertyList : %s
                  |____ links : %s""".formatted(this.name,  this.propertyList, this.links);
    }
}
