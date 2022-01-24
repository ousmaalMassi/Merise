package com.mcd;

import java.util.*;

/**
 * 
 */
public class Cardinality {

    /**
     * Default constructor
     */
    public Cardinality() {
    }

    /**
     * 
     */
    private Cardinalities typeCard;



    /**
     *
     */
    public enum Cardinalities {
        ZERO_ONE("0, 1"),
        ZERO_MANY("0, n"),
        ONE_ONE("1, 1"),
        ONE_MANY("1, n");

        String card;

        Cardinalities(String card) {
            this.card = card;
        }
    }

}