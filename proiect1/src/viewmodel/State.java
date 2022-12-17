package viewmodel;

import movie.Movie;
import page.Page;
import user.User;

import java.util.ArrayList;

public final class State {
    public Page page;
    public User user;
    public ArrayList<Movie> movies;

    private final static State instance = new State();

    private State() {}

    public static State getInstance() {
        return instance;
    }

}
