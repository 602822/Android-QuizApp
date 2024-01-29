package com.example.oblig1quizapp;

public class Dog {
    private int imageResource;
    private String imageText;

    public Dog(int imageResource, String imageText) {
        this.imageResource = imageResource;
        this.imageText = imageText;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getImageText() {
        return imageText;
    }
}
