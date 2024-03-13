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

    //private Uri imageUri;
// error: Cannot figure out how to save this field into database. You can consider adding a type converter for it.
//    private Uri imageUri;

    public DogEntity(@NonNull String imageText, int imageResource,  @Nullable String imageUri) {
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

    @Nullable
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(@Nullable String imageUri) {
        this.imageUri = imageUri;
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
