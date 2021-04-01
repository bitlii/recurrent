package com.bitco.recurrent.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Item implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

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
        this.interval = interval;

        DateTime now = new DateTime();
        if (now.isBefore(lastOccurrence)) {
            this.lastOccurrence = lastOccurrence.minusDays(interval);
        } else {
            this.lastOccurrence = lastOccurrence;
        }
    }

    /**
     * Get integer number of days until the next occurrence of item.
     * @return day until next occurrence.
     */
    public int getDaysUntilNextOccurrence() {
        DateTime now = new DateTime();

        DateTime nextOccurrence = lastOccurrence.plusDays(interval);
        Days daysUntil = Days.daysBetween(now, nextOccurrence);

        return daysUntil.getDays();
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


    // ----- Parcelable Methods
    public Item(Parcel source) {
        this.id = source.readLong();
        this.name = source.readString();
        this.description = source.readString();
        this.amount = source.readDouble();
        this.type = TransactionType.valueOf(source.readString());
        this.lastOccurrence = DateTime.parse(source.readString());
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
