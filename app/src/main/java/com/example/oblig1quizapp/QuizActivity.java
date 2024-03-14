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


    DogEntity dog;
    List<String> imageTexts;

   // int score;

 //   int totalRounds;

    QuizViewModel quizViewModel;

    List<DogEntity> dogs;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set layout based on orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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






        /*

        score = 0;
        totalRounds = 1;
*/

       // imageTexts = new ArrayList<>();







        /*

        quizViewModel.getAllDogsAsc().observe(this, dogs -> {
            if(dogs != null) {
                this.dogs = new ArrayList<>(dogs);
                dog = pickRandomDog();
                Uri imageUri = Converters.fromString(dog.getImageUri());
                int imageResource = dog.getImageResource();
                ImageView image = findViewById(R.id.quizDogImage);



                if (imageResource != 0) {
                    image.setImageResource(imageResource);
                } else if (imageUri != null) {
                    image.setImageURI(imageUri);
                } else {
                    Log.d("test", "No image URI or imageResource");
                }


                fillImageTextsArray();
                addTextToButtons();

            }
        });
*/

       // int score = mDogViewModel.getScore().getValue();


        /*
        String scoreText = "Your Score: " + score;
        scoreView.setText(scoreText);
*/




     //   int roundsPlayed = mDogViewModel.getRoundsplayed().getValue();
     //   TextView rounds = findViewById(R.id.rounds);
     //   String roundText = "Round: " + roundsPlayed;
       // rounds.setText(roundText);













    }


    /*
    public DogEntity pickRandomDog() {
       // List<Dog> dogs = DogList.dogs;
        if (dogs == null || dogs.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(dogs.size());

        return dogs.get(randomIndex);
    }

*/

    /*
    //Get two image texts that is not the image texts of the Dog on the screen
    public void fillImageTextsArray() {
       // List<Dog> dogs = DogList.dogs;
        imageTexts.clear();


        Random random = new Random();


        while (imageTexts.size() != 2) {
            int randomIndex = random.nextInt(dogs.size());
            DogEntity randomDog = dogs.get(randomIndex);

            if (randomDog != dog && !imageTexts.contains(randomDog.getImageText())) {
                String imageText = randomDog.getImageText();
                imageTexts.add(imageText);
            }
        }

    }
*/

    /*
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
*/

    /*

    //Compare the text of the dog on the screen to the text of the button clicked
    public void checkAnswer(Button button) {
        TextView answerView = findViewById(R.id.correctAnswerOrNot);

        if (button.getText().equals(dog.getImageText())) {
            answerView.setText("Correct Answer!");
           // TextView scoreView = findViewById(R.id.scoreText);
            int currentScore =  quizViewModel.getScore().getValue();
            quizViewModel.setScore(new MutableLiveData<>(currentScore + 1));
            Log.d("quizDebug", "Score: " + currentScore);
          //  String scoreText = "Your score: " + score;
          //  scoreView.setText(scoreText);
        } else {
            answerView.setText("The correct answer was: " + dog.getImageText());
        }
    }
*/

    /*
    public void playAgain() {
        DogEntity newDog;
        //makes sure the new dog picked is not the same as the last dog picked
        do {
            newDog = pickRandomDog();
        } while (newDog.equals(dog));

        dog = newDog;
        ImageView dogimageView = findViewById(R.id.quizDogImage);

        int imageResource = newDog.getImageResource();
        Uri imageUri = Converters.fromString(newDog.getImageUri());

        //Update Round
        /*
        TextView rounds = findViewById(R.id.rounds);
        totalRounds++;
        String roundText = "Round: " + totalRounds;
        rounds.setText(roundText);


        //update Rounds played
        int roundsPlayed = quizViewModel.getRoundsplayed().getValue();
        quizViewModel.setRoundsplayed(new MutableLiveData<>(roundsPlayed + 1));
        Log.d("quizDebug", "Rounds played: " + roundsPlayed);
*/

/*
        //Set the Dog image
        if (imageResource != 0) {
            dogimageView.setImageResource(newDog.getImageResource());
        } else if (newDog.getImageUri() != null) {
            dogimageView.setImageURI(imageUri);
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        //Update the buttons
        fillImageTextsArray();
        addTextToButtons();

    }
*/

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