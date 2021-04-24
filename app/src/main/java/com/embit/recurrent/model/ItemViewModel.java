package com.embit.recurrent.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.embit.recurrent.R;
import com.embit.recurrent.repository.ItemRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Flowable;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private Flowable<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public void deleteAll(Item item) {
        repository.deleteAll();
    }

    public Flowable<List<Item>> getAllItems() {
        SharedPreferences prefs = getApplication().getApplicationContext().getSharedPreferences(getApplication().getString(R.string.SHARED_PREF_KEY), Context.MODE_PRIVATE);
        String initialSort = prefs.getString("itemSort", "name");

        switch (initialSort) {
            case "name": {
                return getAllItemsByName();
            }
            case "amount": {
                return getAllItemsByAmount();
            }
            case "occurrence": {
                return getAllItemsByNextOccurrence();
            }
            default:
                return allItems;
        }
    }

    public Flowable<List<Item>> getAllItemsByAmount() {
        SharedPreferences prefs = getApplication().getApplicationContext().getSharedPreferences(getApplication().getString(R.string.SHARED_PREF_KEY), Context.MODE_PRIVATE);
        prefs.edit().putString("itemSort", "amount").apply();

        allItems = repository.getAllItemsByAmount();
        return allItems;
    }

    public Flowable<List<Item>> getAllItemsByName() {
        SharedPreferences prefs = getApplication().getApplicationContext().getSharedPreferences(getApplication().getString(R.string.SHARED_PREF_KEY), Context.MODE_PRIVATE);
        prefs.edit().putString("itemSort", "name").apply();

        allItems = repository.getAllItemsByName();
        return allItems;
    }

    public Flowable<List<Item>> getAllItemsByNextOccurrence() {
        SharedPreferences prefs = getApplication().getApplicationContext().getSharedPreferences(getApplication().getString(R.string.SHARED_PREF_KEY), Context.MODE_PRIVATE);
        prefs.edit().putString("itemSort", "occurrence").apply();

        allItems = repository.getAllItemsByNextOccurrence();
        return allItems;
    }

}
