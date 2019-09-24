package com.example.sqlite_crud.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sqlite_crud.model.Movie;

import java.util.ArrayList;

import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "db_IMDB";

    // table name
    private static final String TABLE_NAME = "tb_movie";

    // column tables
    private static final String COLOUMN_ID = "id";
    private static final String COLOUMN_NAME = "name";
    private static final String COLOUMN_GENRE = "genre";
    private static final String COLOUMN_TIMESTAMP = "timestamp";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TB_MOVIE_TABLE;
        CREATE_TB_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                                + COLOUMN_ID + " INTEGER PRIMARY KEY," + COLOUMN_NAME+ " TEXT,"
                                + COLOUMN_GENRE + " TEXT,"    + COLOUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_TB_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_IF_EXIST;
        DROP_IF_EXIST = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_IF_EXIST);
    }

    public long insertData(String movieName, String movieGenre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLOUMN_NAME, movieName);
        cv.put(COLOUMN_GENRE, movieGenre);
        long id = db.insert(TABLE_NAME,null, cv);
        db.close();
        return id;
    }

    public int updateData(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLOUMN_NAME, movie.getMovieName());
        cv.put(COLOUMN_GENRE, movie.getMovieGenre());

        // updating row
        return db.update(TABLE_NAME, cv, "id" + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    public Movie getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLOUMN_ID, COLOUMN_NAME, COLOUMN_GENRE, COLOUMN_TIMESTAMP},
                COLOUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Movie data = new Movie(
                cursor.getInt(cursor.getColumnIndex(COLOUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLOUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLOUMN_GENRE)),
                cursor.getString(cursor.getColumnIndex(COLOUMN_TIMESTAMP)));



        // close the db connection
        cursor.close();

        return data;
    }

    public ArrayList<Movie> getAllDatas() {
        ArrayList<Movie> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLOUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(COLOUMN_ID)));
                movie.setMovieName(cursor.getString(cursor.getColumnIndex(COLOUMN_NAME)));
                movie.setMovieGenre(cursor.getString(cursor.getColumnIndex(COLOUMN_GENRE)));
                movie.setTimeStamp(cursor.getString(cursor.getColumnIndex(COLOUMN_TIMESTAMP)));

                movies.add(movie);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return movies list
        return movies;
    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteData(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLOUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }
}
