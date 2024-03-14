package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {


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


        galleryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);

        });

        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });


    }


}