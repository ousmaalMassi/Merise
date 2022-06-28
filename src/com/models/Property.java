package com.models;


/**
 * 
 */
public class Property {

    private String code;

    private String name;

    private Types type;

    private int length;

    public Property(String name, Types type, int length) {
        this.code = this.normalize(name);
        this.name = name;
        this.type = type;
        this.length = length;
    }

    public enum Types {
        ALPHABETICAL,
        ALPHANUMERIC,
        NUMERIC,
        DATE,
        LOGIC
    }

    public enum Constraints {
        PK("PRIMARY KEY"),
        FK("FOREIGN KEY"),
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

    private String normalize(String name) {
        return name.replaceAll(" ", "_").replaceAll("'", "_");
    }

    @Override
    public String toString(){
        return """
                  
                  |________ code : %s,
                  |________ name : %s,
                  |________ type : %s,
                  |________ length : %s
                  """.formatted(this.code, this.name,  this.type, this.length);
    }
}
