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

public abstract class DogRoomDatabase extends RoomDatabase {

    public abstract DogDAO dogDao();

    private static volatile DogRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //run database operations async on a background thread
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


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

