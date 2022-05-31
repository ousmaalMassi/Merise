package com.mcd;

import com.MeriseObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Association extends MeriseObject {

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

    public void setCard(String name, Cardinalities cardinality) {
        this.links.replace(name, cardinality);
    }

    @Override
    public String toString(){
        return """
                  
                  |____ name : %s,
                  |____ propertyList : %s
                  |____ links : %s""".formatted(this.name,  this.propertyList, this.links);
    }
}
