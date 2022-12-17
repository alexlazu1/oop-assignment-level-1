package viewmodel;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;
import page.PageFactory;
import page.PageFactory.PageType;
import user.User;

import java.util.ArrayList;

import static constants.Constants.*;
import static page.PageFactory.PageType.Logout;
import static page.PageFactory.PageType.Movies;

public class Viewmodel {
    Input input;
    State state;
    ArrayNode output;

    ArrayList<User> users;
    ArrayList<Movie> movies;


    private final static Viewmodel instance = new Viewmodel();

    private Viewmodel() {
    }

    public static Viewmodel getInstance() {
        return instance;
    }

    public void initializeViewmodel(Input input, ArrayNode output) {
        this.state = State.getInstance();

        this.input = input;
        this.output = output;


        this.movies = new ArrayList<Movie>();
        for (Movie movie : input.getMovies()) {
            this.movies.add(new Movie(movie));
        }

        this.users = new ArrayList<User>();
        for (UserInput userInput : input.getUsers()) {
            this.users.add(new User(userInput));
        }
    }


    public String doAction(ActionsInput action) {
        if (action.getType().equals(CHANGE_PAGE)) {
            return changePage(action.getPage());
        } else {
            if (!findFeature(action.getFeature()))
                return FEATURE_NOT_FOUND;
            switch (action.getFeature()) {
                case "login" -> {
                    return login(action.getCredentials());
                }
                case "register" -> {
                    return register(action.getCredentials());
                }
                case "search" -> {
                    return search(action.getStartsWith());
                }
                default -> {
                    System.out.println("INVALID COMMAND!!!!!!!!!!!!!!!!!!!\n\n\n\n\n\n");
                    return "INVALID";
                }
            }
        }
    }

    public String search(String startsWith) {
        ArrayList<Movie> newMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getName().startsWith(startsWith))
                newMovies.add(movie);
        }

        state.movies = newMovies;

        return SUCCESS_SEARCH;
    }

    public void resetState() {
        state.page = PageFactory.createPage(PageType.HomeNotAuth);
        state.user = null;
        state.movies = new ArrayList<>();
    }

    public boolean findFeature(String featureName) {
        return state.page.getFeatures().contains(featureName);
    }

    public String register(Credentials credentials) {
        String name = credentials.getName();

        /* Check if user already exists */
        for (User user : users) {
            if (user.getCredentials().getName().equals(name)) {
                resetState();
                return USER_ALREADY_EXISTS;
            }
        }

        /* Added user */
        users.add(new User(credentials));
        return login(credentials);
    }

    public String login(Credentials credentials) {
        String name = credentials.getName();
        String password = credentials.getPassword();

        /* Search the user */
        for (User user : users) {
            if (user.getCredentials().getName().equals(name) && user.getCredentials().getPassword().equals(password)) {
                state.user = user;
                state.page = PageFactory.createPage(PageType.HomeAuth);
                return SUCCESS_LOGIN;
            }
        }

        resetState();
        /* User not found */
        return USER_NOT_FOUND;
    }

    public void loadMovies() {
        state.movies = new ArrayList<>();

        String country = state.user.getCredentials().getCountry();

        for (Movie movie : movies) {
            if (!isMovieBanned(movie, country)) {
                state.movies.add(movie);
            }
        }
    }

    public boolean isMovieBanned(Movie movie, String country) {
        return movie.getCountriesBanned().contains(country);
    }

    public String changePage(String pageName) {
        /* Search the page */
        for (PageType pageType : state.page.getNextPages()) {
            if (pageType.getName().equals(pageName)) {
                System.out.println("Page(" + pageName + ") found");

                if (pageType == Logout) {
                    resetState();
                } else {
                    state.page = PageFactory.createPage(pageType);
                    if (pageType == Movies)
                        return SUCCESS_PAGE_CHANGE_MOVIES;
                    else
                        return SUCCESS_PAGE_CHANGE;
                }
            }
        }

        /* Page not found */
        System.out.println("Page(" + pageName + ") not found");
        return PAGE_NOT_FOUND;
    }

    public ArrayList<Movie> getArrayCopy(ArrayList<Movie> array) {
        ArrayList<Movie> newArr = new ArrayList<>();
        for (Movie movie : array) {
            newArr.add(new Movie(movie));
        }
        return newArr;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
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

}
