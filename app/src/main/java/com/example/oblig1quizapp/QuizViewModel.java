package com.example.oblig1quizapp;

import static com.example.oblig1quizapp.QuizActivity.fillButtonOptionsList;
import static com.example.oblig1quizapp.QuizActivity.pickRandomDog;

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

    private Observer<List<DogEntity>> observer;

    public QuizViewModel(Application application) {
        super(application);
        mRepository = new DogRepository(application);
        mAllDogs = mRepository.getAllDogs();

        buttonOptions.setValue(new ArrayList<>());


        //Will set the initial value for dogPicked and buttonOptions when i have access
        // to the dog objects from room database
        mAllDogs.observeForever( observer = new Observer<List<DogEntity>>() {
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


    public MutableLiveData<Integer> getScore() {
        return score;
    }


    public MutableLiveData<Integer> getRoundsplayed() {
        return roundsplayed;
    }


    public LiveData<List<String>> getButtonOptions() {
        return buttonOptions;
    }


    public MutableLiveData<DogEntity> getDogPicked() {
        return dogPicked;
    }


    public void setDogPicked(MutableLiveData<DogEntity> dogPicked) {
        this.dogPicked = dogPicked;
    }


    //Avoids memory leaks
    @Override
    protected void onCleared() {
        super.onCleared();
        mAllDogs.removeObserver(observer);
    }
}


