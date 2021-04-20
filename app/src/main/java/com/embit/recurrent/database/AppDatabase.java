package com.embit.recurrent.database;

import android.content.Context;
import android.os.AsyncTask;

import com.embit.recurrent.dao.ItemDao;
import com.embit.recurrent.model.Item;
import com.embit.recurrent.model.TransactionType;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "recurrent-database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack) // todo:remove this before release - adds items to database.
                    .build();
        }
        return instance;
    }

    /**
     * Prepopulate database for testing purposes.
     */
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsync(instance).execute();
        }
    };

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private PopulateDatabaseAsync(AppDatabase db) {
            this.itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Spark", "Phone Bill", 30.00, TransactionType.EXPENSE, LocalDate.now(), 30));
            itemDao.insert(new Item("Snap Fitness", "Gym", 34.50, TransactionType.EXPENSE, LocalDate.parse("2021-04-05"), 14));
            itemDao.insert(new Item("City Fitness", "Gym again.", 14.95, TransactionType.EXPENSE, LocalDate.parse("2021-04-15"), 14));
            return null;
        }
    }

}
