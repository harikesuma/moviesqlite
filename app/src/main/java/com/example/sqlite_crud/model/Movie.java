package com.example.sqlite_crud.model;

public class Movie {
    private int id;
    private String movieName;
    private String movieGenre;
    private String timeStamp;

    public Movie(int id, String movieName, String movieGenre, String timeStamp){
        this.id = id;
        this.movieName = movieName;
        this.movieGenre = movieGenre;
        this.timeStamp = timeStamp;
    }

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
