package com.bitco.recurrent.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {

    @PrimaryKey
    private int id;

    private String name;
    private String description;
    private double amount;
    private TransactionType type;

    private Date lastOccurrence; // last date of occurrence.
    private int interval; // time between each occurrence.


}
