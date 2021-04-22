package com.embit.recurrent.repository;

import android.app.Application;

import com.embit.recurrent.dao.ItemDao;
import com.embit.recurrent.database.AppDatabase;
import com.embit.recurrent.model.Item;

import java.util.List;

import io.reactivex.Flowable;

public class ItemRepository {

    private ItemDao itemDao;
    private Flowable<List<Item>> allItems;

    public ItemRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        itemDao = db.itemDao();
        allItems = itemDao.getAll();
    }

    public void insert(Item item) {
        itemDao.insert(item);
    }

    public void update(Item item) {
        itemDao.update(item);
    }

    public void delete(Item item) {
        itemDao.delete(item);
    }

    public void deleteAll() {
        itemDao.deleteAll();
    }

    public Flowable<List<Item>> getAllItems() {
        return allItems;
    }

    public Flowable<List<Item>> getAllItemsByAmount() {
        return itemDao.getAllByAmount();

    }

    public Flowable<List<Item>> getAllItemsByName() {
        return itemDao.getAllByName();

    }

    public Flowable<List<Item>> getAllItemsByNextOccurrence() {
        return itemDao.getAllByNextOccurrence();

    }

}
