package com.example.sqlite_crud.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite_crud.FormAddActivity;
import com.example.sqlite_crud.FormEditActivity;
import com.example.sqlite_crud.MainActivity;
import com.example.sqlite_crud.R;
import com.example.sqlite_crud.databaseHelper.DatabaseHandler;
import com.example.sqlite_crud.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter< MovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;
    private DatabaseHandler databaseHandler;

    public MovieAdapter(Context context, ArrayList<Movie> movies, DatabaseHandler dbhandler){
        this.context = context;
        this.movies = movies;
        this.databaseHandler = dbhandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        final String tapedMovieName;
        final String tapedMovieGenre;

        final Movie movie = movies.get(position);

        tapedMovieName = movie.getMovieName();
        tapedMovieGenre = movie.getMovieGenre();

        holder.movieName.setText(movie.getMovieName());
        holder.movieGenre.setText(movie.getMovieGenre());

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(movie.getTimeStamp()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //updateNote("test",position);
                Intent intent = new Intent(context, FormEditActivity.class);
                intent.putExtra("position",String.valueOf(position));
                intent.putExtra("movieName",tapedMovieName);
                intent.putExtra("movieGenre", tapedMovieGenre);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public TextView movieGenre;
        public ImageView delete,edit;
        public TextView timestamp;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            movieGenre = itemView.findViewById(R.id.movieGenre);
            timestamp = itemView.findViewById(R.id.timestamp);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {
            Log.e("error",e.getMessage());
        }
        return "";
    }
    private void deleteNote(int position) {

        // deleting the data from db
        databaseHandler.deleteData(movies.get(position));

        // removing the data from the list
        movies.remove(position);
        MainActivity.notifyAdapter();
    }

    public void showDialogDelete (final int position){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure want to delete this movie?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // deleting the data from db
                        databaseHandler.deleteData(movies.get(position));

                        // removing the data from the list
                        movies.remove(position);
                        MainActivity.notifyAdapter();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
