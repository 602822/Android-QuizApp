package com.example.oblig1quizapp;

import androidx.appcompat.app.AppCompatActivity;

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


    Dog dog;
    List<String> imageTexts;

    int score;

    int totalRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

         dog = pickRandomDog();
         imageTexts = new ArrayList<>();
         score = 0;
         totalRounds = 1;

        Uri imageUri = dog.getImageUri();
        int imageResource = dog.getImageResource();

        TextView scoreView = findViewById(R.id.scoreText);
        String scoreText = "Your Score: " + score;
        scoreView.setText(scoreText);

        TextView rounds = findViewById(R.id.rounds);
        String roundText = "Round: " + totalRounds;
        rounds.setText(roundText);


        ImageView image = findViewById(R.id.quizDogImage);

        if(imageResource != 0) {
            image.setImageResource(imageResource);
        } else if(imageUri != null) {
            image.setImageURI(imageUri);
        } else {
            Log.d("test", "No image URI or imageResource");
        }


        fillImageTextsArray();
        addTextToButtons();

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button.setOnClickListener(v ->
        {

            checkAnswer(button);
            playAgain();

        });

        button2.setOnClickListener(v ->
        {
            checkAnswer(button2);
            playAgain();

        });

        button3.setOnClickListener(v ->
        {
            checkAnswer(button3);
            playAgain();

        });



    }


    public Dog pickRandomDog() {
        List<Dog> dogs = DogList.dogs;
        if (dogs == null || dogs.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(dogs.size());

        return dogs.get(randomIndex);
    }

    //Get two image texts that is not the image texts of the Dog on the screen
    public void fillImageTextsArray() {
    List<Dog> dogs = DogList.dogs;
    imageTexts.clear();


    Random random = new Random();

    while(imageTexts.size() != 2) {
        int randomIndex = random.nextInt(dogs.size());
        Dog randomDog = dogs.get(randomIndex);

        if (randomDog != dog && !imageTexts.contains(randomDog.getImageText())) {
            String imageText = randomDog.getImageText();
            imageTexts.add(imageText);
        }
    }

    }

    public void addTextToButtons() {
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        List<String> shuffeledTexts = new ArrayList<>();

        shuffeledTexts.add(dog.getImageText());
        shuffeledTexts.add(imageTexts.get(0));
        shuffeledTexts.add(imageTexts.get(1));

        Collections.shuffle(shuffeledTexts);

        button1.setText(shuffeledTexts.get(0));
        button2.setText(shuffeledTexts.get(1));
        button3.setText(shuffeledTexts.get(2));
    }


    //Compare the text of the dog on the screen to the text of the button clicked
    public void checkAnswer(Button button) {
        TextView answerView = findViewById(R.id.correctAnswerOrNot);

        if(button.getText().equals(dog.getImageText())) {
           answerView.setText("Correct Answer!");
           TextView scoreView = findViewById(R.id.scoreText);
           score++;
           String scoreText = "Your score: " + score;
           scoreView.setText(scoreText);
        } else {
            answerView.setText("The correct answer was: " + dog.getImageText());
        }
    }

    public void playAgain() {
        Dog newDog;
       //makes sure the new dog picked is not the same as the last dog picked
        do {
            newDog = pickRandomDog();
        } while(newDog.equals(dog));

        dog = newDog;
        ImageView dogimageView = findViewById(R.id.quizDogImage);

        int imageResource = newDog.getImageResource();
        Uri imageUri = newDog.getImageUri();

        //Update Round
        TextView rounds = findViewById(R.id.rounds);
        totalRounds++;
        String roundText = "Round: " + totalRounds;
        rounds.setText(roundText);

        //Set the Dog image
        if(imageResource != 0) {
            dogimageView.setImageResource(newDog.getImageResource());
        } else if(newDog.getImageUri() != null) {
            dogimageView.setImageURI(imageUri);
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        fillImageTextsArray();
        addTextToButtons();

    }

}