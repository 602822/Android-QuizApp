package com.example.oblig1quizapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DogRepository {
    private DogDAO mDogDao;
    private LiveData<List<DogEntity>> mAllDogs;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DogRepository(Application application) {
        DogRoomDatabase db = DogRoomDatabase.getDatabase(application);
        mDogDao = db.dogDao();
        mAllDogs = mDogDao.getAllDogs();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<DogEntity>> getAllDogs() {
        return mAllDogs;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(DogEntity dog) {
        DogRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDogDao.insert(dog);
});
    }
}