package com.graphics.mcd;

public enum GMCDNodeType {
    ENTITY("Entity"),
    ASSOCIATION("Association");

    String gMCDNodeType;

    GMCDNodeType(String gMCDNodeType) {
        this.gMCDNodeType = gMCDNodeType;
    }

    @Override
    public String toString() {
        return gMCDNodeType;
    }
}
