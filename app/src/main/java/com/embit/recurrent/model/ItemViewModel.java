package com.embit.recurrent.model;

import android.app.Application;

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
        return getAllItemsByName(); // default for now - soon i should use the last used sort.
    }

    public Flowable<List<Item>> getAllItemsByAmount() {
        allItems = repository.getAllItemsByAmount();
        return allItems;
    }

    public Flowable<List<Item>> getAllItemsByName() {
        allItems = repository.getAllItemsByName();
        return allItems;
    }

    public Flowable<List<Item>> getAllItemsByNextOccurrence() {
        allItems = repository.getAllItemsByNextOccurrence();
        return allItems;
    }

}
