package com.mcd;

import java.util.*;

/**
 * 
 */
public class Property {

    /**
     * Default constructor
     */
    public Property() {
    }

    /**
     * 
     */
    public String name;

    /**
     * 
     */
    public Types type;

    /**
     * 
     */
    public int length;

    /**
     * 
     */
    public Constraints constraints;



    /**
     * 
     */
    public enum Types {
        INT,
        BIGINT,
        FLOAT,
        DOUBLE,
        VARCHAR,
        TEXT,
        DATE
    }

    /**
     * 
     */
    public enum Constraints {
        PRIMARY_KEY,
        FOREIGN_KEY,
        NULL,
        NOT_NULL,
        UNIQUE,
        AUTO_INCREMENTS
    }

}