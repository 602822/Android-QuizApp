package com.example.oblig1quizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


//NOT USED ANYMORE
public class DogList {
    public static List<Dog> dogs = new ArrayList<>();

    static {
        dogs.add(new Dog(R.drawable.goldenretriever, "Golden Retriever"));
        dogs.add(new Dog(R.drawable.husky, "Husky"));
        dogs.add(new Dog(R.drawable.labradorretriever, "Labrador Retriever"));

    }

    //Sort A-Z
    public static void sortDogsByImageText() {
        dogs.sort(Comparator.comparing(Dog::getImageText));
    }

    //Sort Z-A
    public static void reverseSort() {
        dogs.sort((dog1, dog2) -> dog2.getImageText().compareTo(dog1.getImageText()));
    }


}
