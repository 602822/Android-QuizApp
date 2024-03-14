package com.example.oblig1quizapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void deleteDogWithId(long id) {
        mRepository.deleteDogWithId(id);
    }

    public LiveData<List<DogEntity>> getAllDogsDesc() {
        return mRepository.getAllDogsDesc();
    }

    public LiveData<List<DogEntity>> getAllDogsAsc() {
        return mRepository.getAllDogsAsc();
    }




}
