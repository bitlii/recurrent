package com.bitco.recurrent.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import io.reactivex.Single;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.bitco.recurrent.R;
import com.bitco.recurrent.adapter.ItemAdapter;
import com.bitco.recurrent.database.AppDatabase;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.ItemViewModel;
import com.bitco.recurrent.model.TransactionType;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemViewModel itemViewModel;

    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
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

    }


}