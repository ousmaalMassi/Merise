package com.graphics.gdf;

public enum DFType {
    SIMPLE("Simple"),
    TRIVIAL("Trivial"),
    NON_TRIVIAL("Non-Trivial");

    final String type;

    DFType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
