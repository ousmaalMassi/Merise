package com.mcd;

import com.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Association extends Node {

    private Map<String, Cardinalities> links;

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
    public Map<String, Cardinalities> getLinks() {
        return links;
    }

    /**
     * @param links between Entities and Cardinalities
     */
    public void setLinks(Map<String, Cardinalities> links) {
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
