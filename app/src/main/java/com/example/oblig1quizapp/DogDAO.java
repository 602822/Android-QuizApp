package com.example.oblig1quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDAO {
   @Insert
    void insert(DogEntity dog); //Change later to dog

   @Query("DELETE FROM dog_table")
    void deleteAll();

    @Query("SELECT * FROM dog_table ORDER BY imageText ASC")
      LiveData<List<DogEntity>> getAllDogs(); //Change later to dog

}
