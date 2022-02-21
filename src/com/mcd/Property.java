package com.mcd;

import java.util.List;

/**
 * 
 */
public class Property {

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
    public Property(String name, Types type, int length, List<Constraints> constraints) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.constraints = constraints;
    }

    public Property() {

    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Constraints> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraints> constraints) {
        this.constraints = constraints;
    }

    @Override
    public String toString(){
        return String.format("""
                        
                            {
                                "name" : "%s",
                                "type" : "%s",
                                "length" : %s,
                                "constraints" : %s
                            }
                """, this.name,  this.type, this.length, this.constraints);
    }
}
