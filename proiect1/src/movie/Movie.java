package movie;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class Movie {
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

    public Movie(final Movie movie) {
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

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public void incNumLikes() {
        this.numLikes++;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }


    public void setRating(final double rating) {
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

    public void setRatings(final ArrayList<Double> ratings) {
        this.ratings = ratings;
    }
}

