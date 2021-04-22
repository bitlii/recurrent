package com.embit.recurrent.dao;

import com.embit.recurrent.model.Item;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM Item")
    Flowable<List<Item>> getAll();

    @Query("SELECT * FROM Item ORDER BY amount ASC")
    Flowable<List<Item>> getAllByAmount();

    @Query("SELECT * FROM Item ORDER BY name ASC")
    Flowable<List<Item>> getAllByName();

    @Query("SELECT * FROM Item ORDER BY DATE(lastOccurrence, '+'||interval||' days') ASC")
    Flowable<List<Item>> getAllByNextOccurrence();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    void deleteAll();


}
