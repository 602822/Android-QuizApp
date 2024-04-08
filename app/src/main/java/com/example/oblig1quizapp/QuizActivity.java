package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    static QuizViewModel quizViewModel;

    Button button;
    Button button2;
    Button button3;

    TextView answerView;

    ImageView dogImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //Set layout based on orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.quiz_landscape);
        } else {
            setContentView(R.layout.activity_quiz);
        }

        //Associate the ViewModel with the activity
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        answerView = findViewById(R.id.correctAnswerOrNot);
        dogImageView = findViewById(R.id.quizDogImage);


        //Sets initial score and updated score when it changes
        quizViewModel.getScore().observe(this, score -> {
            if (score != null) {
                TextView scoreView = findViewById(R.id.scoreText);
                scoreView.setText("Your Score: " + score);
            }
        });

        //Sets initial rounds played and updated rounds played when it changes
        quizViewModel.getRoundsplayed().observe(this, roundsPlayed -> {
            if (roundsPlayed != null) {
                TextView rounds = findViewById(R.id.rounds);
                String roundText = "Round: " + roundsPlayed;
                rounds.setText(roundText);
            }
        });


        //will only start the quiz once i have access to the dog objects
        quizViewModel.getAllDogs().observe(this, dogs -> {

            //Logic for initial state of the game
            play();

            //The buttons will now call the checkAnswer and playAgain methods when clicked
            setButtonClickListeners(dogs);
        });


    }


    public void setButtonClickListeners(List<DogEntity> dogs) {


        button.setOnClickListener(v ->
        {

            checkAnswer(button);
            playAgain(dogs);

        });

        button2.setOnClickListener(v ->
        {
            checkAnswer(button2);
            playAgain(dogs);

        });

        button3.setOnClickListener(v ->
        {
            checkAnswer(button3);
            playAgain(dogs);

        });
    }

    public void checkAnswer(Button button) {


        MutableLiveData<Integer> score = quizViewModel.getScore();

        MutableLiveData<DogEntity> dogPicked = quizViewModel.getDogPicked();

        DogEntity dog = dogPicked.getValue();

        if (button.getText().equals(dog.getImageText())) {
            answerView.setText("Correct Answer!");

            int currentScore = score.getValue() != null ? score.getValue() : 0;


            score.setValue(currentScore + 1);

            Log.d("quizDebug", "Score: " + currentScore);

        } else {
            answerView.setText("The correct answer was: " + dog.getImageText());
        }
    }

    public void playAgain(List<DogEntity> dogs) {


        pickRandomDog(dogs);

        MutableLiveData<DogEntity> dogPicked = quizViewModel.getDogPicked();

        DogEntity dog = dogPicked.getValue();

        MutableLiveData<Integer> roundsPlayed = quizViewModel.getRoundsplayed();

        int roudsPlayedValue = roundsPlayed.getValue();


        roundsPlayed.setValue(roudsPlayedValue + 1);


        //Set the Dog image
        if (dog.getImageResource() != 0) {
            //  dogImageView.setImageResource(dog.getImageResource()); //setImageResource reads from disk and will bottleneck the app
            Glide.with(this).load(dog.getImageResource()).into(dogImageView); // use glide to load image from resource async
        } else if (dog.getImageUri() != null) {
            //  dogImageView.setImageURI(imageUri);  // seImageUri reads from disk and will bottleneck the app
            Glide.with(this).load(dog.getImageUri()).into(dogImageView); // use glide to load image from uri async
        } else {
            Log.d("test", "No image URI or imageResource");
        }


        //Update the buttons
        fillButtonOptionsList(dogs);
        addTextToButtons();

    }


    public static void fillButtonOptionsList(List<DogEntity> dogs) {


        List<String> buttonOptions = quizViewModel.getButtonOptions().getValue();
        buttonOptions.clear();

        MutableLiveData<DogEntity> dogPicked = quizViewModel.getDogPicked();

        DogEntity dog = dogPicked.getValue();

        Random random = new Random();


        while (buttonOptions.size() != 2) {
            int randomIndex = random.nextInt(dogs.size());
            DogEntity randomDog = dogs.get(randomIndex);

            if (randomDog != dog && !buttonOptions.contains(randomDog.getImageText())) {
                String imageText = randomDog.getImageText();
                buttonOptions.add(imageText);
            }
        }

    }

    public void addTextToButtons() {

        MutableLiveData<DogEntity> dogPicked = quizViewModel.getDogPicked();

        DogEntity dog = dogPicked.getValue();

        List<String> buttonOptions = quizViewModel.getButtonOptions().getValue();

        List<String> shuffeledTexts = new ArrayList<>();

        shuffeledTexts.add(dog.getImageText());
        shuffeledTexts.add(buttonOptions.get(0));
        shuffeledTexts.add(buttonOptions.get(1));

        Collections.shuffle(shuffeledTexts);

        button.setText(shuffeledTexts.get(0));
        button2.setText(shuffeledTexts.get(1));
        button3.setText(shuffeledTexts.get(2));
    }

    public static void pickRandomDog(List<DogEntity> dogs) { //Sets the value of dogPicked


        DogEntity dog;
        if (dogs == null || dogs.isEmpty()) {
            return;
        }

        Log.d("quizDebug", "dogs: " + dogs.size());


        Random random = new Random();
        int randomIndex = random.nextInt(dogs.size());

        /* Causes the app to crash
        do {
            dog = dogs.get(randomIndex);
        } while (dog.equals(dogPicked.getValue()));
*/
        dog = dogs.get(randomIndex);

        Log.d("quizDebug", "dogPicked: " + dog.getImageText());

        MutableLiveData<DogEntity> dogPicked = new MutableLiveData<>(dog);

        quizViewModel.setDogPicked(dogPicked);


    }

    public void play() {

        MutableLiveData<DogEntity> dogPicked = quizViewModel.getDogPicked();

        DogEntity dog = dogPicked.getValue();

        if (dog.getImageResource() != 0) {
            //  dogimageView.setImageResource(dog.getImageResource()); //setImageResource reads from disk and will bottleneck the app
            Glide.with(this).load(dog.getImageResource()).into(dogImageView); // use glide to load image from resource async
        } else if (dog.getImageUri() != null) {
            // dogimageView.setImageURI(imageUri); // seImageUri reads from disk and will bottleneck the app
            Glide.with(this).load(dog.getImageUri()).into(dogImageView); // use glide to load image from uri async
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        addTextToButtons();

    }


}