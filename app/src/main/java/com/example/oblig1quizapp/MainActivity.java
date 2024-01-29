package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button galleryButton = findViewById(R.id.gallerybutton);
        Button quizButton = findViewById(R.id.quizButton);

        galleryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,GalleryActivity.class);
            startActivity(intent);

        });

        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });


    }



}