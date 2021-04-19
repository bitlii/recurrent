package com.bitco.recurrent.model;

/**
 * Enum of what kind of transaction the item is doing.
 */
public enum TransactionType {
    NONE(""),
    INCOME("INCOME"),
    EXPENSE("EXPENSE");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
