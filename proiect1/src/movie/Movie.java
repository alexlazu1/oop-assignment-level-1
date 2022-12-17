package movie;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> countriesBanned;
    private ArrayList<String> actors;
    @JsonIgnore
    private ArrayList<Double> ratings = new ArrayList<>();

    private int numLikes = 0;
    private int numRatings = 0;
    private double rating = 0;

    public Movie() {
    }

    public Movie(Movie movie) {
        this.name = movie.name;
        this.year = movie.year;
        this.duration = movie.duration;
        this.genres = movie.genres;
        this.countriesBanned = movie.countriesBanned;
        this.actors = movie.actors;
        this.numLikes = movie.numLikes;
        this.numRatings = movie.numRatings;
        this.rating = movie.rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public void incNumLikes() {
        this.numLikes++;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    public void setRating(double rating) {
        this.rating = rating;
    }

    public void calculateRating() {
        if (ratings.size() == 0) {
            System.out.println("ERROR, SHOULDN T REACH THIS IN RATINGS\n\n\n\n\n");
            return;
        }

        double sum = 0;

        for (Double rate : ratings)
            sum += rate;

        this.rating = sum / ratings.size();
        this.numRatings++;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }
}

