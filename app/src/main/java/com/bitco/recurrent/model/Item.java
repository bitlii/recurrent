package com.bitco.recurrent.model;

import com.bitco.recurrent.database.Converters;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import lombok.Data;

@Entity
public class Item {

    @PrimaryKey
    private int id;

    private String name;
    private String description;
    private double amount;

    @TypeConverters(Converters.class)
    private TransactionType type;

    private String lastOccurrence; // last date of occurrence. format is YYYY-MM-DD
    private int interval; // time between each occurrence.

    public Item(String name, String description, double amount, TransactionType type, String lastOccurrence, int interval) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.lastOccurrence = lastOccurrence;
        this.interval = interval;
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

    public String getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(String lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
