package com.example.oblig1quizapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        pickImageLauncher =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();

                    if(intent != null) {

                        Uri selectedImageUri = intent.getData();
                        if(selectedImageUri != null) {
                            int takeFlags = intent.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                            EditText editText = findViewById(R.id.textinput);
                            String imageText =  editText.getText().toString();
                            Dog dog = new Dog(selectedImageUri,imageText);
                            DogList.dogs.add(dog);
                            Log.d("test", DogList.dogs.toString());
                        }


                    }
                }
            }
        });


        GridView gridView = findViewById(R.id.gridview);

        List<Dog> dogs = DogList.dogs;


        ImageTextAdapter adapter = new ImageTextAdapter(this,dogs);
        gridView.setAdapter(adapter);

        Button button = findViewById(R.id.addbutton);

        button.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
          pickImageLauncher.launch(intent);
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            adapter.removeItem(position);
        });


        Button sortButton = findViewById(R.id.sortbutton);

        //Switches between using sort and reverse sort
        final boolean[] sortAscending = {true};
        sortButton.setOnClickListener(v -> {
            if(sortAscending[0]) {
                DogList.sortDogsByImageText();
            } else {
                DogList.reverseSort();
            }
            adapter.notifyDataSetChanged();
            sortAscending[0] = !sortAscending[0];
        });



    }
}