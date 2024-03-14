package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    QuizViewModel quizViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set layout based on orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.quiz_landscape);
        } else {
            setContentView(R.layout.activity_quiz);
        }

        //Assosiate the ViewModel with the activity
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        //Sets initial score and updated score when it changes
        quizViewModel.getScore().observe(this, score -> {
            Log.d("quizDebug", "The score changed! Score: " + score);
            if (score != null) {
                TextView scoreView = findViewById(R.id.scoreText);
                scoreView.setText("Your Score: " + score);
            }
        });

        //Sets initial rounds played and updated rounds played when it changes
        quizViewModel.getRoundsplayed().observe(this, roundsPlayed -> {
            Log.d("quizDebug", "The rounds played changed! Rounds played: " + roundsPlayed);
            if (roundsPlayed != null) {
                TextView rounds = findViewById(R.id.rounds);
                String roundText = "Round: " + roundsPlayed;
                rounds.setText(roundText);
            }
        });


        ImageView dogImageView = findViewById(R.id.quizDogImage);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        //Logic for initial state of the game
        //will only start the quiz once i have access to the dog objects
        quizViewModel.getAllDogs().observe(this, dogs -> {
            quizViewModel.play(dogImageView, button, button2, button3);
        });


        //The buttons will now call the checkAnswer and playAgain methods when clicked
        setButtonClickListeners();


    }


    public void setButtonClickListeners() {


        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        TextView answerView = findViewById(R.id.correctAnswerOrNot);

        ImageView dogImageView = findViewById(R.id.quizDogImage);

        button.setOnClickListener(v ->
        {

            quizViewModel.checkAnswer(button, answerView);
            quizViewModel.playAgain(dogImageView, button, button2, button3);

        });

        button2.setOnClickListener(v ->
        {
            quizViewModel.checkAnswer(button2, answerView);
            quizViewModel.playAgain(dogImageView, button, button2, button3);

        });

        button3.setOnClickListener(v ->
        {
            quizViewModel.checkAnswer(button3, answerView);
            quizViewModel.playAgain(dogImageView, button, button2, button3);

        });
    }

}