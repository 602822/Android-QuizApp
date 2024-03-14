package com.example.oblig1quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dog_table")
public class DogEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int imageResource;
    @NonNull
    private String imageText;

    @Nullable
    private String imageUri;


    public DogEntity(@NonNull String imageText, int imageResource, @Nullable String imageUri) {
        this.imageText = imageText;
        this.imageResource = imageResource;
        this.imageUri = imageUri;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }


    @NonNull
    public String getImageText() {
        return imageText;
    }


    @Nullable
    public String getImageUri() {
        return imageUri;
    }


    @Override
    public String toString() {
        return "DogEntity{" +
                "id=" + id +
                ", imageResource=" + imageResource +
                ", imageText='" + imageText + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }
}
