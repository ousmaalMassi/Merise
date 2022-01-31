package com.mcd;

public enum Cardinalities {
    ZERO_ONE("0, 1"),
    ZERO_MANY("0, n"),
    ONE_ONE("1, 1"),
    ONE_MANY("1, n");

    private final String card;
    public static final Cardinalities DEFAULT_CARDINALITY = Cardinalities.ONE_MANY;

    Cardinalities(String card) {
        this.card = card;
    }
}
