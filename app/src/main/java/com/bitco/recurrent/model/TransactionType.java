package com.bitco.recurrent.model;

/**
 * Enum of what kind of transaction the item is doing.
 */
public enum TransactionType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    NONE("");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
