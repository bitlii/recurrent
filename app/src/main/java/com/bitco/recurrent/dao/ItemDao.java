package com.bitco.recurrent.dao;

import com.bitco.recurrent.model.Item;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertItems(Item... items);

}
