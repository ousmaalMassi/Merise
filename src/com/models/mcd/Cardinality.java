package com.models.mcd;

public enum Cardinality {
    ZERO_ONE("0, 1"),
    ZERO_MANY("0, n"),
    ONE_ONE("1, 1"),
    ONE_MANY("1, n");

    private final String card;
    public static final Cardinality DEFAULT_CARDINALITY = Cardinality.ONE_MANY;

    Cardinality(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return this.card;
    }

}
