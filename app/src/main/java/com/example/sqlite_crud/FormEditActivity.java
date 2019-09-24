package com.example.sqlite_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_crud.databaseHelper.DatabaseHandler;
import com.example.sqlite_crud.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FormEditActivity extends AppCompatActivity {

    EditText movieNameET, movieGenreET;
    Button button;
    String movieNameData, movieGenreData;
    DatabaseHandler db;
    String str_position;
    String str_movieName;
    String str_movieGenre;
    String toastUpdated;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<Movie> List = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);

        setContentView(R.layout.activity_form_edit);
        movieNameET= findViewById(R.id.movieName);
        movieGenreET= findViewById(R.id.movieGenre);

        button = findViewById(R.id.btnUpdate);
        Bundle bundle = getIntent().getExtras();
        str_position = bundle.getString("position");
        str_movieName = bundle.getString("movieName");
        str_movieGenre = bundle.getString("movieGenre");

        movieNameET.setText(str_movieName);
        movieGenreET.setText(str_movieGenre);



        position = Integer.parseInt(str_position);
        db = new DatabaseHandler(this);
        List.addAll(db.getAllDatas());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = List.get(position);
                // updating data text
                movie.setMovieName(movieNameET.getText().toString());
                movie.setMovieGenre(movieGenreET.getText().toString());
                // updating data in db
                db.updateData(movie);
                MainActivity.notifyAdapter();
                toastUpdated = movieNameET.getText().toString();
                Toast.makeText(getApplicationContext(), toastUpdated + " Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormEditActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    }

