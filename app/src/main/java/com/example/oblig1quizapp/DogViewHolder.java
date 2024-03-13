package com.example.oblig1quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DogViewHolder extends RecyclerView.ViewHolder {
    private final TextView dogTextView;
    private final ImageView dogImageView;

    private DogViewHolder(View itemView) {
        super(itemView);
        dogTextView = itemView.findViewById(R.id.textView);
        dogImageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(String text, int imageResource) {
        dogTextView.setText(text);
        dogImageView.setImageResource(imageResource);
    }

    static DogViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new DogViewHolder(view);
    }
}
