package com.bitco.recurrent.model;

import android.app.Application;

import com.bitco.recurrent.repository.ItemRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

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

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

}
