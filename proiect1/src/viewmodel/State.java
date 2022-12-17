package viewmodel;

import input.Movie;
import page.Page;
import page.PageFactory;
import user.User;

import java.util.ArrayList;

public class State {
    public Page page;
    public User user;
    public ArrayList<Movie> movies;

    private final static State instance = new State();

    private State() {
        page = PageFactory.createPage(PageFactory.PageType.HomeNotAuth);
        user = null;
        movies = new ArrayList<>();
    }

    public static State getInstance() {
        return instance;
    }

}
