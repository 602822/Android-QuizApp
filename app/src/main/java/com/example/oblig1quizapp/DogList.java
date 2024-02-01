package com.example.oblig1quizapp;

import java.util.ArrayList;
import java.util.List;

public class DogList
{
    public static List<Dog> dogs = new ArrayList<>();

    static {
        dogs.add(new Dog(R.drawable.goldenretriever, "Golden Retriever"));
        dogs.add(new Dog(R.drawable.husky, "Husky"));
        dogs.add(new Dog(R.drawable.labradorretriever, "Labrador Retriever"));

    }


}
