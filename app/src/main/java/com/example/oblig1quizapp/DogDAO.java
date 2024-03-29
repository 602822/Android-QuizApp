package com.example.oblig1quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDAO {
    @Insert
    void insert(DogEntity dog);

    @Query("DELETE FROM dog_table")
    void deleteAll();

    @Query("DELETE FROM dog_table WHERE  id = :id")
    void deleteDogWithId(long id);

    @Query("SELECT * FROM dog_table")
    LiveData<List<DogEntity>> getAllDogs();

    @Query("SELECT * FROM dog_table ORDER BY imageText DESC")
    LiveData<List<DogEntity>> getAllDogsDesc();

    @Query("SELECT * FROM dog_table ORDER BY imageText ASC")
    LiveData<List<DogEntity>> getAllDogsAsc();

}
