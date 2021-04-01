package com.bitco.recurrent.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.bitco.recurrent.dao.ItemDao;
import com.bitco.recurrent.database.AppDatabase;
import com.bitco.recurrent.model.Item;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ItemRepository {

    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        itemDao = db.itemDao();
        allItems = itemDao.getAll();
    }

    public void insert(Item item) {
        new InsertItemAsync(itemDao).execute(item);
    }

    public void update(Item item) {
        new UpdateItemAsync(itemDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsync(itemDao).execute(item);
    }

    public void deleteAll() {
        new DeleteAllItemsAsync(itemDao).execute();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public static class InsertItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private InsertItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    public static class UpdateItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private UpdateItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    public static class DeleteItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private DeleteItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    public static class DeleteAllItemsAsync extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private DeleteAllItemsAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAll();
            return null;
        }
    }
}
