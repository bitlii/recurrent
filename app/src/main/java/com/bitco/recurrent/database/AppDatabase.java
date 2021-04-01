package com.bitco.recurrent.database;

import android.content.Context;
import android.os.AsyncTask;

import com.bitco.recurrent.dao.ItemDao;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.TransactionType;

import org.joda.time.DateTime;

import java.util.logging.Logger;

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
            System.out.println("HERERERER");

            itemDao.insert(new Item("Spark", "Phone Bill", 30.00, TransactionType.EXPENSE, new DateTime(), 30));
            itemDao.insert(new Item("Snap Fitness", "Gym", 34.50, TransactionType.EXPENSE, new DateTime(), 14));
            itemDao.insert(new Item("City Fitness", "Gym again.", 14.95, TransactionType.EXPENSE, new DateTime(), 14));
            return null;
        }
    }

}
