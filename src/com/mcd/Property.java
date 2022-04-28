package com.mcd;

import java.util.List;

/**
 * 
 */
public class Property {

    public String code;

    public String name;

    public Types type;

    public int length;

    public List<Constraints> constraints;

    public Property(String name, Types type, int length, List<Constraints> constraints) {
        this.code = this.normalize(name);
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
    /*public enum Types {
        INT,
        BIGINT,
        FLOAT,
        DOUBLE,
        VARCHAR,
        TEXT,
        DATE
    }*/

    public enum Types {
        ALPHABETICAL,
        ALPHANUMERIC,
        DIGITAL,
        DATE,
        LOGIC
    }

    public enum Constraints {
        PRIMARY_KEY("PRIMARY KEY"),
        NULL("NULL"),
        NOT_NULL("NOT NULL"),
        UNIQUE("UNIQUE"),
        AUTO_INCREMENT("AUTO_INCREMENT");

        private final String constraint;
        Constraints(String constraint) {
            this.constraint = constraint;
        }
        @Override
        public String toString() {
            return this.constraint;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.code = this.normalize(name);
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

    private String normalize(String name) {
        return name.replaceAll(" ", "_").replaceAll("'", "_");
    }

    /*@Override
    public String toString(){
        return String.format("""
                        
                            {
                                "code" : "%s",
                                "name" : "%s",
                                "type" : "%s",
                                "length" : %s,
                                "constraints" : %s
                            }
                """, this.code, this.name,  this.type, this.length, this.constraints);
    }*/

    @Override
    public String toString(){
        return """
                  
                  |________ code : %s,
                  |________ name : %s,
                  |________ type : %s,
                  |________ length : %s,
                  |________ constraints : %s""".formatted(this.code, this.name,  this.type, this.length, this.constraints);
    }
}
