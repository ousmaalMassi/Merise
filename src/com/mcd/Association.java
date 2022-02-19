package com.mcd;

import com.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Association extends Node {

    /**
     *
     */
    private Map<Entity, Cardinalities> links;


    /**
     * Default constructor
     */
    public Association(String name) {
        super(name);
        this.links = new HashMap<>();
    }


    /**
     *
     */
    public void addLink(Entity entity, Cardinalities cardinalities){
        links.put(entity, cardinalities);
    }

    public void removeLink(Entity entity){
        links.remove(entity);
    }


    /**
     * @return List of Links
     */
    public Map<Entity, Cardinalities> getLinks() {
        return links;
    }

    /**
     * @param links between Entities and Cardinalities
     */
    public void setLinks(Map<Entity, Cardinalities> links) {
        this.links = links;
    }


    @Override
    public String toString(){
        return String.format("""
                    {
                        "name" : "%s",
                        "propertyList" : %s,
                        "parties" : %s
                    }
                """, this.name,  this.propertyList, this.links);
    }
}
