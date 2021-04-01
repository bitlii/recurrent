package com.bitco.recurrent.model;

import com.bitco.recurrent.database.Converters;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private double amount;

    @TypeConverters(Converters.class)
    private TransactionType type;

    @TypeConverters(Converters.class)
    private DateTime lastOccurrence; // last date of occurrence. format is YYYY-MM-DD
    private int interval; // time between each occurrence.

    public Item(String name, String description, double amount, TransactionType type, DateTime lastOccurrence, int interval) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.lastOccurrence = lastOccurrence;
        this.interval = interval;
    }

    /**
     * Get integer number of days until the next occurrence of item.
     * @return day until next occurrence.
     */
    public int getDaysUntilNextOccurrence() {
        DateTime date = new DateTime();
        lastOccurrence.plusDays(interval);
        Days daysUntil = Days.daysBetween(date, lastOccurrence);

        return daysUntil.getDays();
    }

    // --- GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public DateTime getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(DateTime lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
