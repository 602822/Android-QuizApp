package com.example.oblig1quizapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickImageLauncher;

    private DogViewModel mDogViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.gallery_landscape);
        } else {
            setContentView(R.layout.activity_gallery);
        }


        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();

                    if (intent != null) {

                        Uri selectedImageUri = intent.getData();
                        if (selectedImageUri != null) {
                            int takeFlags = intent.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                            EditText editText = findViewById(R.id.textinput);
                            String imageText = editText.getText().toString();

                            String imageUri = Converters.uriToString(selectedImageUri);
                            DogEntity dog = new DogEntity(imageText, 0, imageUri);
                            mDogViewModel.insert(dog);

                        }


                    }
                }
            }
        });


        GridView gridView = findViewById(R.id.gridview);
        mDogViewModel = new ViewModelProvider(this).get(DogViewModel.class);


        ImageTextAdapter adapter = new ImageTextAdapter(this);
        gridView.setAdapter(adapter);


        mDogViewModel.getAllDogs().observe(this,
                new Observer<List<DogEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<DogEntity> dogs) {
                        Log.d("fixBug", "Number of dogs: " + dogs.size());
                        adapter.setList(dogs);
                    }
                });


        Button button = findViewById(R.id.addbutton);

        button.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {


            DogEntity dog = (DogEntity) adapter.getItem(position);
            int dogId = dog.getId();
            mDogViewModel.deleteDogWithId(dogId);
        });


        Button sortButton = findViewById(R.id.sortbutton);

        //Switches between using sort and reverse sort
        final boolean[] sortAscending = {true};
        sortButton.setOnClickListener(v -> {
            if (sortAscending[0]) {
                mDogViewModel.getAllDogsAsc().observe(this, dogs -> {
                    adapter.setList(dogs);
                });
            } else {
                mDogViewModel.getAllDogsDesc().observe(this, dogs -> {
                    adapter.setList(dogs);
                });
            }
            adapter.notifyDataSetChanged();
            sortAscending[0] = !sortAscending[0];
        });


    }
}