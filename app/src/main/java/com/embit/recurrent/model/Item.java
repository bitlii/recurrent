package com.embit.recurrent.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.embit.recurrent.database.Converters;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Class that represents an item entity.
 */
@Entity
public class Item implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String description;
    private double amount;

    @TypeConverters(Converters.class)
    private TransactionType type;

    @TypeConverters(Converters.class)
    private LocalDate lastOccurrence; // last date of occurrence. format is YYYY-MM-DD
    private int interval; // time between each occurrence.

    public Item(String name, String description, double amount, TransactionType type, LocalDate lastOccurrence, int interval) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.interval = interval;

        LocalDate now = LocalDate.now();
        if (now.isBefore(lastOccurrence)) {
            this.lastOccurrence = lastOccurrence.minusDays(interval);
        } else {
            this.lastOccurrence = lastOccurrence;
        }
    }

    /**
     * Update the occurrence to match the latest last occurrence of the item.
     */
    public void updateOccurrence() {
        LocalDate now = LocalDate.now();
        LocalDate next = lastOccurrence.plusDays(interval);

        while (now.isAfter(next)) {
            this.lastOccurrence = next;
            next = lastOccurrence.plusDays(interval);
        }
    }

    /**
     * Get integer number of days until the next occurrence of item.
     * @return day until next occurrence.
     */
    public int getDaysUntilNextOccurrence() {
        LocalDate now = LocalDate.now();

        LocalDate nextOccurrence = lastOccurrence.plusDays(interval);
        return (int) ChronoUnit.DAYS.between(now, nextOccurrence);
    }

    // --- GETTERS & SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDate getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(LocalDate lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }


    // ----- Parcelable Methods
    public Item(Parcel source) {
        this.id = source.readLong();
        this.name = source.readString();
        this.description = source.readString();
        this.amount = source.readDouble();
        this.type = TransactionType.valueOf(source.readString());
        this.lastOccurrence = LocalDate.parse(source.readString());
        this.interval = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(type.toString());
        dest.writeString(lastOccurrence.toString());
        dest.writeInt(interval);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

}
