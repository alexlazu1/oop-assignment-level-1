package input;

import java.util.ArrayList;

public class Input {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionsInput> actions;

    public Input() {
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieInput> movies) {
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
