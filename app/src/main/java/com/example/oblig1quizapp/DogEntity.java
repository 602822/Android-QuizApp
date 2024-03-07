package com.example.oblig1quizapp;

import android.net.Uri;

import androidx.annotation.NonNull;
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
    private Uri imageUri;


    public DogEntity(int id, int imageResource, @NonNull String imageText, Uri imageUri) {
        this.id = id;
        this.imageResource = imageResource;
        this.imageText = imageText;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    @NonNull
    public String getImageText() {
        return imageText;
    }

    public void setImageText(@NonNull String imageText) {
        this.imageText = imageText;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
