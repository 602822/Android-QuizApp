package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   // private DogRoomDatabase database;
    private DogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button galleryButton = findViewById(R.id.gallerybutton);
        Button quizButton = findViewById(R.id.quizButton);



        repository = new DogRepository(getApplication());

        //add default dog objects if the database is empty
        repository.getAllDogs().observe(this, dogs -> {
            if (dogs.isEmpty()) {
                repository.insert(new DogEntity("Golden Retriever", R.drawable.goldenretriever, ""));
                repository.insert(new DogEntity("Husky", R.drawable.husky, ""));
                repository.insert(new DogEntity("Labrador Retriever", R.drawable.labradorretriever, ""));
            }
        });


        //database = DogRoomDatabase.getDatabase(getApplicationContext());


     //   LiveData<List<DogEntity>> allDogs = database.dogDao().getAllDogs();

        //add default objects to database if it is empty

        /*
        allDogs.observe(this, dogs -> {
            if (dogs.isEmpty()) {
                insertDefaultObjects();
            }
        });
*/



        galleryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);

        });

        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });


    }


    /*
    public void insertDefaultObjects() {
        Log.d("bug","ADDED DEFAULT DOGS");

        DogEntity dog2 = new DogEntity("Husky", R.drawable.husky, "");
        DogEntity dog3 = new DogEntity("Labrador Retriever", R.drawable.labradorretriever, "");

        DogRoomDatabase.databaseWriteExecutor.execute(() -> {
            database.dogDao().insert(dog);
            database.dogDao().insert(dog2);
            database.dogDao().insert(dog3);
        });

    }
*/
/*
    public void clearDatabase() {
        DogRoomDatabase.databaseWriteExecutor.execute(() -> {
            database.dogDao().deleteAll();
        });
    }
*/

}