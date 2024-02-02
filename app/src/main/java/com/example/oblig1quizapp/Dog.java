package com.example.oblig1quizapp;

import android.net.Uri;

public class Dog {
    private int imageResource;
    private String imageText;
    private Uri imageUri;

    public Dog(int imageResource, String imageText) {
        this.imageResource = imageResource;
        this.imageText = imageText;
    }

    public Dog(Uri imageUri, String imageText) {
        this.imageUri = imageUri;
        this.imageText = imageText;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getImageText() {
        return imageText;
    }


    @Override
    public String toString() {
        return imageText;
    }
}
