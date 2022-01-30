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

    public void addParty(Entity entity, Cardinalities cardinalities){
        parties.put(entity, cardinalities);
    }

    /**
     *
     */
    Map<Entity, Cardinalities> parties;

}