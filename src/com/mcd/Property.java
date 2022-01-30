package com.mcd;

import java.util.*;

/**
 * 
 */
public class Property {

    /**
     * 
     */
    public Property(String name, Types type, int length, List<Constraints> constraints) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.constraints = constraints;
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
    public List<Constraints> constraints;



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