package com.example.oblig1quizapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DogViewModel extends AndroidViewModel {
    private DogRepository mRepository;

    private final LiveData<List<DogEntity>> mAllDogs;

    public DogViewModel (Application application) {
        super(application);
        mRepository = new DogRepository(application);
        mAllDogs = mRepository.getAllDogs();
    }

    LiveData<List<DogEntity>> getAllDogs() { return mAllDogs; }

    public void insert(DogEntity dog) { mRepository.insert(dog); }
}
