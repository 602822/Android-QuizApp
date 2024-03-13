package com.example.oblig1quizapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {DogEntity.class}, version = 1, exportSchema = false) // Change later to dog
//@TypeConverters({Converters.class})
public abstract class DogRoomDatabase extends RoomDatabase {

    public abstract DogDAO dogDao();

    private static volatile DogRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //run database operations async on a background thread
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);



    /*
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("debug","callback oncreate executed");

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                DogDAO dogDAO = INSTANCE.dogDao();
                dogDAO.deleteAll();

                DogEntity dog = new DogEntity( "Golden Retriever", R.drawable.goldenretriever, "");
                dogDAO.insert(dog);
                dog = new DogEntity( "Husky", R.drawable.husky, "");
                dogDAO.insert(dog);
                dog = new DogEntity("Labrador Retriever", R.drawable.labradorretriever, "");
                dogDAO.insert(dog);
                Log.d("debug","dogs inserted");
            });
        }
    };


    static Migration migration1to2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Perform the necessary database schema changes here
            // For example, execute SQL queries to add/remove columns, tables, etc.
            database.execSQL("ALTER TABLE dog_table ADD COLUMN imageUri TEXT");

        }
    };
*/

    static DogRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DogRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DogRoomDatabase.class, "dog_database")
                            .fallbackToDestructiveMigration()
                            .build();


                }
            }
        }
        return INSTANCE;
    }
}

