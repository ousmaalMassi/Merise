package com.models;

import java.io.Serial;
import java.io.Serializable;

public class MeriseObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
