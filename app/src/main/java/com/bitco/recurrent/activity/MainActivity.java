package com.bitco.recurrent.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitco.recurrent.R;
import com.bitco.recurrent.adapter.ItemAdapter;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.ItemViewModel;
import com.bitco.recurrent.utils.BroadcastManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

/**
 * Main activity of the app.
 */
public class MainActivity extends AppCompatActivity {
    public static final int ADD_ITEM_REQUEST = 1;
    public static final int EDIT_ITEM_REQUEST = 2;
    public static final String EXTRA_EDIT_ITEM = "EDIT_ITEM";

    private final String CHANNEL_ID = "channel_1";

    private ItemViewModel itemViewModel;

    private RecyclerView recycler;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add new item.
        FloatingActionButton buttonAddItem = findViewById(R.id.fabAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEditItemActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });

        /// Recycler View
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

        // Update last occurrences date of each item, then remove the observe to prevent it occurring again and again.
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                for (Item item: itemList) {
                    item.updateOccurrence();
                    itemViewModel.update(item);
                }
                itemViewModel.getAllItems().removeObserver(this);
            }
        });

        // Card movement that does actions depending on swiping direction.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Delete
                if (direction == ItemTouchHelper.RIGHT) {
                    itemViewModel.delete(itemAdapter.getItemAtPos(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Item deleted.", Toast.LENGTH_SHORT).show();
                }
                // Edit
                else if (direction == ItemTouchHelper.LEFT) {
                    Item item = itemAdapter.getItemAtPos(viewHolder.getAdapterPosition());
                    itemAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                    intent.putExtra(MainActivity.EXTRA_EDIT_ITEM, item);
                    startActivityForResult(intent, EDIT_ITEM_REQUEST);
                }
            }
        }).attachToRecyclerView(recycler);


//        Notifications Testing.
//        Intent intent = new Intent(this, BroadcastManager.class);
//        PendingIntent pending = PendingIntent.getBroadcast(this, 42, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//
//        Calendar c = Calendar.getInstance();
//        LocalDateTime now = LocalDateTime.now();
//        c.set(Calendar.HOUR_OF_DAY, now.getHour());
//        c.set(Calendar.MINUTE, now.getMinute() + 2);
//        c.set(Calendar.SECOND, 0);
//
//        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pending);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            Item newItem = data.getParcelableExtra(AddEditItemActivity.EXTRA_SAVED_ITEM);
            itemViewModel.insert(newItem);
        }
        else if (requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK) {
            Item editedItem = data.getParcelableExtra(AddEditItemActivity.EXTRA_SAVED_ITEM);
            editedItem.updateOccurrence();
            itemViewModel.update(editedItem);
        }

    }

    /**
     * Create a new notification channel, but only on API 26+.
     * @return notification manager, if device API is 26+.
     */
    private NotificationManager createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Main Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("channel1", CHANNEL_ID, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            return notificationManager;
        }
        return null;
    }


}