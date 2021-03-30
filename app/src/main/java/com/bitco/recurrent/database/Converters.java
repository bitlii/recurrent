package com.bitco.recurrent.database;

import com.bitco.recurrent.model.TransactionType;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Used to convert enums into a storable entry for the database.
 */
public class Converters {

    // Transaction Types

    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        return type.toString();
    }

    @TypeConverter
    public static TransactionType toTransactionType(String type) {
        if (type.toUpperCase().equals("INCOME")) {
            return TransactionType.INCOME;
        }
        else if (type.toUpperCase().equals("PAYMENT")) {
            return TransactionType.EXPENSE;
        }
        return TransactionType.NONE;
    }
}
