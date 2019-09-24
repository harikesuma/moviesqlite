package com.example.sqlite_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_crud.databaseHelper.DatabaseHandler;

public class FormAddActivity extends AppCompatActivity {
    EditText movieNameET, movieGenreET;
    Button button;
    String movieNameData, movieGenreData;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add);
        movieNameET = findViewById(R.id.movieName);
        movieGenreET = findViewById(R.id.movieGenre);

        button = findViewById(R.id.btnSubmit);
        db = new DatabaseHandler(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieNameData = movieNameET.getText().toString();
                movieGenreData = movieGenreET.getText().toString();
                db.insertData(movieNameData,movieGenreData);
                Toast.makeText(getApplicationContext(), movieNameData + " Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormAddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    }
