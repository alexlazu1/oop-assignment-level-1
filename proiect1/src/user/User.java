package user;

import input.Credentials;
import input.MovieInput;
import input.UserInput;
import viewmodel.Viewmodel;

import java.util.ArrayList;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<MovieInput> purchasedMovies = new ArrayList<>();
    private ArrayList<MovieInput> watchedMovies= new ArrayList<>();
    private ArrayList<MovieInput> likedMovies= new ArrayList<>();
    private ArrayList<MovieInput> ratedMovies= new ArrayList<>();

    public User() {
    }

    public User(UserInput user) {
        this.credentials = user.getCredentials();
    }

    public User(Credentials credentials) {
        this.credentials = credentials;
    }

    public User(User user) {
        Viewmodel viewmodel = Viewmodel.getInstance();
        this.credentials = user.credentials;
        this.tokensCount = user.tokensCount;
        this.numFreePremiumMovies = user.numFreePremiumMovies;
        this.purchasedMovies = viewmodel.getArrayCopy(user.purchasedMovies);
        this.watchedMovies = viewmodel.getArrayCopy(user.watchedMovies);
        this.likedMovies = viewmodel.getArrayCopy(user.likedMovies);
        this.ratedMovies = viewmodel.getArrayCopy(user.ratedMovies);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<MovieInput> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<MovieInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieInput> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<MovieInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieInput> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<MovieInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieInput> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<MovieInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    @Override
    public String toString() {
        return "User{" +
                "credentials=" + credentials +
                ", tokensCount=" + tokensCount +
                ", numFreePremiumMovies=" + numFreePremiumMovies +
                ", purchasedMovies=" + purchasedMovies +
                ", watchedMovies=" + watchedMovies +
                ", likedMovies=" + likedMovies +
                ", ratedMovies=" + ratedMovies +
                '}';
    }
}
