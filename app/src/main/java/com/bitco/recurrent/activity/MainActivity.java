package com.bitco.recurrent.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bitco.recurrent.R;
import com.bitco.recurrent.adapter.ItemAdapter;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ITEM_REQUEST = 1;

    private ItemViewModel itemViewModel;

    private RecyclerView recycler;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.itemRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        itemAdapter = new ItemAdapter();
        recycler.setAdapter(itemAdapter);

        itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                itemAdapter.setItemList(itemList);
            }
        });

        FloatingActionButton buttonAddItem = findViewById(R.id.fabAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            Item newItem = data.getParcelableExtra(AddItemActivity.ADD_ITEM_DATA);
            itemViewModel.insert(newItem);
        }
    }
}