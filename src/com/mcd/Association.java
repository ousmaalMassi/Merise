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
    }


    Map<Entity, Cardinality> parties;

}