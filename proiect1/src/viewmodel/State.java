package viewmodel;

import input.MovieInput;
import page.Page;
import page.PageFactory;
import user.User;

import java.util.ArrayList;

public class State {
    public Page page;
    public User user;
    public ArrayList<MovieInput> movies;

    public State() {
        page = PageFactory.createPage(PageFactory.PageType.HomeNotAuth);
        user = null;
        movies = new ArrayList<>();
    }

}
