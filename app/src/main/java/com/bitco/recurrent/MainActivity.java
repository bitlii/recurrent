package com.bitco.recurrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;

import com.bitco.recurrent.database.AppDatabase;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "recurrent-db").build();

        AsyncTask.execute(() -> {
            Item i = new Item("Spark", "Phone Bill", 30.00, TransactionType.EXPENSE, "2021-03-30", 30);
            db.itemDao().insertItems(i);
        });

    }
}