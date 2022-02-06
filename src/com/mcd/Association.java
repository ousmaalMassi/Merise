package com.mcd;

import java.util.*;

/**
 * 
 */
public class Association extends Node {

    /**
     *
     */
    private Map<String, Cardinalities> links;


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
    public void addLinks(String entity, Cardinalities cardinalities){
        links.put(entity, cardinalities);
    }


    /**
     * @return List of Links
     */
    public Map<String, Cardinalities> getLinks() {
        return links;
    }

    /**
     * @param links
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
