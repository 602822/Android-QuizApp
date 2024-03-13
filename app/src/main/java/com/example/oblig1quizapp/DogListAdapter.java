package com.example.oblig1quizapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


//NOT USED
public class DogListAdapter extends ListAdapter<DogEntity, DogViewHolder> {

    public DogListAdapter(@NonNull DiffUtil.ItemCallback<DogEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        DogEntity current = getItem(position);
        holder.bind(current.getImageText(), current.getImageResource());
    }

    static class DogDiff extends DiffUtil.ItemCallback<DogEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull DogEntity oldDog, @NonNull DogEntity newDog) {
            return oldDog == newDog;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DogEntity oldDog, @NonNull DogEntity newDog) {
            return oldDog.getImageText().equals(newDog.getImageText());
        }
    }
}