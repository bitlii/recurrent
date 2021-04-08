package com.bitco.recurrent.database;

import com.bitco.recurrent.model.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import androidx.room.TypeConverter;

/**
 * Used to convert enums into a storable entry for the database.
 */
public class Converters {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    // ----- Transaction Types

    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        return type.toString();
    }

    @TypeConverter
    public static TransactionType toTransactionType(String type) {
        if (type.toUpperCase().equals("INCOME")) {
            return TransactionType.INCOME;
        }
        else if (type.toUpperCase().equals("EXPENSE")) {
            return TransactionType.EXPENSE;
        }
        return TransactionType.NONE;
    }

    // ----- LocalDate

    @TypeConverter
    public static String fromDateTime(LocalDate date) {
        return date.toString();
    }

    @TypeConverter
    public static LocalDate toDateTime(String date) {
        return LocalDate.parse(date, DATE_FORMAT);
    }

}
