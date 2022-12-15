package input;

import movie.Movie;
import user.User;

import java.util.ArrayList;

public class Input {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<ActionsInput> actions;

    public Input() {
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Input{" +
                "users=" + users +
                ", movies=" + movies +
                ", actions=" + actions +
                '}';
    }
}
