package com.bitco.recurrent.dao;

import com.bitco.recurrent.model.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getAll();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    void deleteAll();


}
