package com.mcd;

import java.util.*;

/**
 * 
 */
public class Association extends Node {

    /**
     * Default constructor
     */
    public Association(String name) {
        super(name);
        this.parties = new HashMap<>();
    }

    public void addParty(String entity, Cardinalities cardinalities){
        parties.put(entity, cardinalities);
    }

    /**
     *
     */
    Map<String, Cardinalities> parties;


    @Override
    public String toString(){
        return String.format("""
                    {
                        "name" : "%s",
                        "propertyList" : %s,
                        "parties" : %s
                    }
                """, this.name,  this.propertyList, this.parties);
    }
}