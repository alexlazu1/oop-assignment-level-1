package viewmodel;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;
import movie.Filter;
import movie.FilterContains;
import movie.FilterSort;
import movie.Movie;
import page.PageFactory;
import page.PageFactory.PageType;
import user.User;

import java.util.ArrayList;
import java.util.Objects;

import static constants.Constants.*;
import static page.PageFactory.PageType.*;

public class Viewmodel {
    Input input;
    State state;
    ArrayNode output;

    ArrayList<User> users;
    ArrayList<Movie> movies;
    ActionsInput action;

    private final static Viewmodel instance = new Viewmodel();

    private Viewmodel() {
    }

    public static Viewmodel getInstance() {
        return instance;
    }

    public void initializeViewmodel(Input input, ArrayNode output) {
        this.state = State.getInstance();
        resetState();

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
        this.action = action;

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
                case "filter" -> {
                    return filter(action.getFilters());
                }
                case "buy tokens" -> {
                    return buyTokens(action.getCount());
                }
                default -> {
                    System.out.println("INVALID COMMAND!!!!!!!!!!!!!!!!!!!\n\n\n\n\n\n");
                    return "INVALID";
                }
            }
        }
    }

    public String buyTokens(String count) {
        int tokens = Integer.parseInt(count);
        state.user.addTokens(tokens);

        return SUCCESS_BUY_TOKENS;
    }

    public String filter(Filter filter) {
        loadMovies();
        ArrayList<Movie> newMovies = new ArrayList<>();
        FilterSort filterSort = filter.getSort();
        FilterContains filterContains = filter.getContains();

        if (filterContains != null) {
            ArrayList<String> actors = filterContains.getActors();
            ArrayList<String> genre = filterContains.getGenre();
            for (Movie movie : state.movies) {
                if ((actors != null && !movie.getActors().containsAll(actors))
                        || (genre != null && !movie.getGenres().containsAll(genre)))
                    continue;
                newMovies.add(movie);
            }
        } else {
            newMovies = state.movies;
        }
        state.movies = newMovies;

        if (filterSort != null) {
            int ratingSort = (filterSort.getRating().equals("increasing")) ? 1 : -1;
            int durationSort = (filterSort.getDuration().equals("increasing")) ? 1 : -1;
            state.movies.sort((m1, m2) -> {
                if (m1.getRating() != m2.getRating())
                    return (int) (ratingSort * m1.getRating() - ratingSort * m2.getRating());
                else {
                    return durationSort * m1.getDuration() - durationSort * m2.getDuration();
                }
            });
        }

        return SUCCESS_FILTER;
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


    public void resetMovies() {
        state.movies = new ArrayList<>();
    }

    public String changePage(String pageName) {
        /* Search the page */
        for (PageType pageType : state.page.getNextPages()) {
            if (pageType.getName().equals(pageName)) {
                System.out.println("Page(" + pageName + ") found");

                // we leave movies Page
                if (state.page.getType() == Movies) {
                    resetMovies();
                }

                if (pageType == Logout) {
                    resetState();
                    return SUCCESS_PAGE_CHANGE;
                } else if (pageType == Movies) {
                    state.page = PageFactory.createPage(pageType);
                    return SUCCESS_PAGE_CHANGE_MOVIES;
                } else if (pageType == SeeDetails) {

                    for (Movie movie : state.movies) {
                        if (Objects.equals(movie.getName(), action.getMovie())) {
                            state.movies = new ArrayList<>();
                            state.movies.add(new Movie(movie));
                            state.page = PageFactory.createPage(pageType);
                            return SUCCESS_SEE_DETAILS;
                        }
                    }
                    return MOVIE_NOT_FOUND;

                } else {
                    state.page = PageFactory.createPage(pageType);
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