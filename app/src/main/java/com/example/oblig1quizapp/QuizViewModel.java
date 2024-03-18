package com.example.oblig1quizapp;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizViewModel extends AndroidViewModel {
    private DogRepository mRepository;

    private final LiveData<List<DogEntity>> mAllDogs;

    private MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private MutableLiveData<Integer> roundsplayed = new MutableLiveData<>(1);
    private MutableLiveData<DogEntity> dogPicked = new MutableLiveData<>();

    private MutableLiveData<List<String>> buttonOptions = new MutableLiveData<>();

    //  private MutableLiveData<List<DogEntity>> dogs = new MutableLiveData<>();


    public QuizViewModel(Application application) {
        super(application);
        mRepository = new DogRepository(application);
        mAllDogs = mRepository.getAllDogs();

        buttonOptions.setValue(new ArrayList<>());


        //Will set the initial value for dogPicked and buttonOptions when i have access
        // to the dog objects from room database
        mAllDogs.observeForever(new Observer<List<DogEntity>>() {
            @Override
            public void onChanged(List<DogEntity> dogs) {
                if (dogs != null && !dogs.isEmpty()) {

                    pickRandomDog(dogs);
                    fillButtonOptionsList(dogs);
                }
            }
        });


    }


    LiveData<List<DogEntity>> getAllDogs() {
        return mAllDogs;
    }


    public LiveData<Integer> getScore() {
        return score;
    }


    public LiveData<Integer> getRoundsplayed() {
        return roundsplayed;
    }


    public LiveData<List<String>> getButtonOptions() {
        return buttonOptions;
    }


    public void play(ImageView dogimageView, Button button1, Button button2, Button button3) {

        DogEntity dog = dogPicked.getValue();

        int imageResource = dog.getImageResource();
        Uri imageUri = Converters.fromString(dog.getImageUri());

        if (imageResource != 0) {
            dogimageView.setImageResource(dog.getImageResource());
        } else if (dog.getImageUri() != null) {
            dogimageView.setImageURI(imageUri);
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        addTextToButtons(button1, button2, button3);

    }


    public void pickRandomDog(List<DogEntity> dogs) { //Sets the value of dogPicked
        // List<DogEntity> dogs = mAllDogs.getValue();
        DogEntity dog;
        if (dogs == null || dogs.isEmpty()) {
            return;
        }

        Log.d("quizDebug", "dogs: " + dogs.size());


        Random random = new Random();
        int randomIndex = random.nextInt(dogs.size());

        do {
            dog = dogs.get(randomIndex);
        } while (dog.equals(dogPicked.getValue()));

        Log.d("quizDebug", "dogPicked: " + dog.getImageText());
        dogPicked.setValue(dog);

    }

    public void fillButtonOptionsList(List<DogEntity> dogs) {


        List<String> buttonOptions = getButtonOptions().getValue();
        buttonOptions.clear();

        // List<DogEntity> dogs = mAllDogs.getValue();

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

    public void addTextToButtons(Button button1, Button button2, Button button3) {


        DogEntity dog = dogPicked.getValue();

        List<String> buttonOptions = getButtonOptions().getValue();

        List<String> shuffeledTexts = new ArrayList<>();

        shuffeledTexts.add(dog.getImageText());
        shuffeledTexts.add(buttonOptions.get(0));
        shuffeledTexts.add(buttonOptions.get(1));

        Collections.shuffle(shuffeledTexts);

        button1.setText(shuffeledTexts.get(0));
        button2.setText(shuffeledTexts.get(1));
        button3.setText(shuffeledTexts.get(2));
    }


    //Compare the text of the dog on the screen to the text of the button clicked
    public void checkAnswer(Button button, TextView answerView) {


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

    public void playAgain(ImageView dogImageView, Button button1, Button button2, Button button3, List<DogEntity> dogs, Context context) {


        pickRandomDog(dogs);

        DogEntity dog = dogPicked.getValue();


        int imageResource = dog.getImageResource();
        Uri imageUri = Converters.fromString(dog.getImageUri());


        //update Rounds played
        int roundsPlayed = roundsplayed.getValue() != null ? roundsplayed.getValue() : 1;
        roundsplayed.setValue(roundsPlayed + 1);
        Log.d("quizDebug", "Rounds played: " + roundsPlayed);


        //Set the Dog image
        if (imageResource != 0) {
            dogImageView.setImageResource(dog.getImageResource());
        } else if (dog.getImageUri() != null) {
          //  dogImageView.setImageURI(imageUri);  // seImageUri reads from disk and will bottleneck the app
            Glide.with(context).load(imageUri).into(dogImageView); // use glide to load image from uri async
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        //Update the buttons
        fillButtonOptionsList(dogs);
        addTextToButtons(button1, button2, button3);

    }

    public void setButtonClickListeners(List<DogEntity> dogs, Button button, Button button2, Button button3, TextView answerView, ImageView dogImageView, Context context) {


        button.setOnClickListener(v ->
        {

            checkAnswer(button, answerView);
            playAgain(dogImageView, button, button2, button3, dogs, context); ////!!!!!!!

        });

        button2.setOnClickListener(v ->
        {
            checkAnswer(button2, answerView);
            playAgain(dogImageView, button, button2, button3, dogs, context);

        });

        button3.setOnClickListener(v ->
        {
            checkAnswer(button3, answerView);
            playAgain(dogImageView, button, button2, button3, dogs, context);

        });
    }

}


