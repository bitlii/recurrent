package com.bitco.recurrent.database;

import com.bitco.recurrent.model.TransactionType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Used to convert enums into a storable entry for the database.
 */
public class Converters {

    private final static String DATE_PATTERN = "YYYY-MM-dd";

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
        else if (type.toUpperCase().equals("EXPENSE")) {
            return TransactionType.EXPENSE;
        }
        return TransactionType.NONE;
    }

    @TypeConverter
    public static String fromDateTime(DateTime dateTime) {
        return dateTime.toString(DATE_PATTERN);
    }

    @TypeConverter
    public static DateTime toDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        return DateTime.parse(date, formatter);
    }

}
