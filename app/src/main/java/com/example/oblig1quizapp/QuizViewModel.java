package com.example.oblig1quizapp;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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



        public QuizViewModel (Application application) {
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

                        pickRandomDog();
                        fillImageTextsArray();
                    }
                }
            });



        }




    LiveData<List<DogEntity>> getAllDogs() { return mAllDogs; }


        public LiveData<Integer> getScore() {
            return score;
        }



        public LiveData<Integer> getRoundsplayed() {
            return roundsplayed;
        }



        public LiveData<DogEntity> getDogPicked() {
            return dogPicked;
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

          addTextToButtons(button1,button2,button3);

        }




        private void pickRandomDog() { //Sets the value of dogPicked
            List<DogEntity> dogs = mAllDogs.getValue();
            DogEntity dog;
            if (dogs == null || dogs.isEmpty()) {
                return ;
            }

            Log.d("quizDebug", "dogs: " + dogs.size());

         //   dogPicked.setValue(dog);
          //  return dog;

            Random random = new Random();
            int randomIndex = random.nextInt(dogs.size());

            do {
                 dog = dogs.get(randomIndex);
            } while( dog.equals(dogPicked.getValue()));

            Log.d("quizDebug", "dogPicked: " + dog.getImageText());
            dogPicked.setValue(dog);

        }

    public void fillImageTextsArray() {

            // List<Dog> dogs = DogList.dogs;
            List<String> buttonOptions = getButtonOptions().getValue();
            buttonOptions.clear();

            List<DogEntity> dogs = mAllDogs.getValue();

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

       /* Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
*/
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
      //  TextView answerView = findViewById(R.id.correctAnswerOrNot);

        DogEntity dog = dogPicked.getValue();

        if (button.getText().equals(dog.getImageText())) {
            answerView.setText("Correct Answer!");
            // TextView scoreView = findViewById(R.id.scoreText);
            int currentScore =  score.getValue() != null ? score.getValue() : 0;
            score.setValue(currentScore + 1);
            Log.d("quizDebug", "Score: " + currentScore);
            //  String scoreText = "Your score: " + score;
            //  scoreView.setText(scoreText);
        } else {
            answerView.setText("The correct answer was: " + dog.getImageText());
        }
    }

    public void playAgain(ImageView dogImageView, Button button1, Button button2, Button button3) {

       // DogEntity newDog;

       // List<DogEntity> dogs = mAllDogs.getValue();

        pickRandomDog();

        DogEntity dog = dogPicked.getValue();


        //makes sure the new dog picked is not the same as the last dog picked

      /*
        do {
            newDog = pickRandomDog();

        } while (newDog.equals(dog));

        dog = newDog;
        */


       // ImageView dogimageView = findViewById(R.id.quizDogImage);

        int imageResource = dog.getImageResource();
        Uri imageUri = Converters.fromString(dog.getImageUri());

        //Update Round
        /*
        TextView rounds = findViewById(R.id.rounds);
        totalRounds++;
        String roundText = "Round: " + totalRounds;
        rounds.setText(roundText);
*/

        //update Rounds played
        int roundsPlayed = roundsplayed.getValue() != null ? roundsplayed.getValue() : 1;
          roundsplayed.setValue(roundsPlayed + 1);
        Log.d("quizDebug", "Rounds played: " + roundsPlayed);



        //Set the Dog image
        if (imageResource != 0) {
            dogImageView.setImageResource(dog.getImageResource());
        } else if (dog.getImageUri() != null) {
            dogImageView.setImageURI(imageUri);
        } else {
            Log.d("test", "No image URI or imageResource");
        }

        //Update the buttons
        fillImageTextsArray();
        addTextToButtons(button1,button2, button3);

    }



}


