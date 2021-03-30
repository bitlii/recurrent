package com.bitco.recurrent.database;

import com.bitco.recurrent.model.TransactionType;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    String fromTransactionType(TransactionType type) {
        return type.toString();
    }

    @TypeConverter
    TransactionType toTransactionType(String type) {
        if (type.toUpperCase().equals("INCOME")) {
            return TransactionType.INCOME;
        }
        else if (type.toUpperCase().equals("PAYMENT")) {
            return TransactionType.EXPENSE;
        }
        return TransactionType.NONE;
    }

}
