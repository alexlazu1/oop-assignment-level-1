package viewmodel;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;
import movie.*;
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
    Movie currentMovie;

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
        this.currentMovie = null;

        this.movies = getArrayCopy(input.getMovies());

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
            if (!findFeature(action.getFeature())) {
//                System.out.println("Did not find feature: (" + action.getFeature() + ") for page: (" + state.page.getType() + ")");
                return FEATURE_NOT_FOUND;
            }
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
                case "purchase" -> {
                    return purchase(action.getMovie());
                }
                case "watch" -> {
                    return watch(action.getMovie());
                }
                case "like" -> {
                    return like(action.getMovie());
                }
                case "rate" -> {
                    return rate(action.getMovie(), action.getRate());
                }
                case "buy premium account" -> {
                    return buyPremiumAccount();
                }
                default -> {
                    System.out.println("INVALID COMMAND!!!!!!!!!!!!!!!!!!!" + action.getFeature() + "\n\n\n\n\n\n");
                    return "INVALID";
                }
            }
        }
    }

    public String buyPremiumAccount() {
        User user = state.user;
        if (user.getCredentials().getAccountType().equals("premium") || user.getTokensCount() < 10)
            return ERROR_BUY_PREMIUM;

        user.addTokens(-10);
        user.getCredentials().setAccountType("premium");
        return SUCCESS_BUY_PREMIUM;
    }

    public String rate(String movieName, int rate) {
        if (movieName != null && !currentMovie.getName().equals(movieName))
            return ERROR_RATE_INVALID_MOVIE;

        if (rate > 5 || rate < 0)
            return ERROR_RATE_INVALID_RATE;

        User user = state.user;
        if (!user.getWatchedMovies().contains(currentMovie))
            return ERROR_RATE_NOT_WATCHED;

        currentMovie.getRatings().add((double) rate);
        currentMovie.calculateRating();
        user.getRatedMovies().add(currentMovie);
        return SUCCESS_RATE_MOVIE;
    }

    public String like(String movieName) {
        if (movieName != null && !currentMovie.getName().equals(movieName))
            return ERROR_LIKE_INVALID_MOVIE;

        User user = state.user;
        if (!user.getWatchedMovies().contains(currentMovie))
            return ERROR_LIKE_NOT_WATCHED;

        currentMovie.incNumLikes();
        user.getLikedMovies().add(currentMovie);
        return SUCCESS_LIKE_MOVIE;
    }

    public String watch(String movieName) {
        if (movieName != null && !currentMovie.getName().equals(movieName))
            return ERROR_WATCH_INVALID_MOVIE;

        User user = state.user;
        if (user.getWatchedMovies().contains(currentMovie))
            return ERROR_WATCH_ALREADY_WATCHED;

        if (!user.getPurchasedMovies().contains(currentMovie))
            return ERROR_WATCH_NOT_PURCHASED;

        user.getWatchedMovies().add(currentMovie);
        return SUCCESS_WATCH_MOVIE;
    }

    public String purchase(String movieName) {
        if (movieName != null && !currentMovie.getName().equals(movieName))
            return ERROR_PURCHASE_INVALID_MOVIE;

        User user = state.user;
        if (user.getPurchasedMovies().contains(currentMovie))
            return ERROR_PURCHASE_ALREADY_BOUGHT;

        boolean isPremium = user.getCredentials().getAccountType().equals("premium");

        if (isPremium && user.getNumFreePremiumMovies() > 0) {
            user.decrementNumFreePremiumMovies();
            user.getPurchasedMovies().add(currentMovie);
            return SUCCESS_PURCHASE_MOVIE;
        } else if (user.getTokensCount() >= 2) {
            user.addTokens(-2);
            user.getPurchasedMovies().add(currentMovie);
            return SUCCESS_PURCHASE_MOVIE;
        }

        return ERROR_PURCHASE_CURRENCY;
    }


    public String buyTokens(String count) {
        int tokens = Integer.parseInt(count), balance = Integer.parseInt(state.user.getCredentials().getBalance());

        if (balance < tokens)
            return ERROR_BUY_TOKENS;

        state.user.getCredentials().changeBalance(tokens);
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
            state.movies.sort((m1, m2) -> {
                int comp = 0;

                if (filterSort.getDuration() != null) {
                    int durationSort = (filterSort.getDuration().equals("increasing")) ? 1 : -1;

                    comp = durationSort * m1.getDuration() - durationSort * m2.getDuration();

                    if (comp != 0)
                        return comp;
                }
                if (filterSort.getRating() != null) {
                    int ratingSort = (filterSort.getRating().equals("increasing")) ? 1 : -1;

                    comp = (int) (ratingSort * m1.getRating() - ratingSort * m2.getRating());
                }
                return comp;
            });
        }

        return SUCCESS_FILTER;
    }

    public String search(String startsWith) {
        ArrayList<Movie> newMovies = new ArrayList<>();
        loadMovies();

        for (Movie movie : state.movies) {
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

                if (pageType == Logout) {
                    resetState();

                    return SUCCESS_PAGE_CHANGE;
                } else if (pageType == Movies) {
                    state.page = PageFactory.createPage(pageType);

                    return SUCCESS_PAGE_CHANGE_MOVIES;
                } else if (pageType == PageType.SeeDetails) {
                    System.out.println("See details of movie: (" + action.getMovie() + ")\n\n");

                    for (Movie movie : state.movies) {
//                        System.out.println("Movie name:" + movie.getName());

                        if (Objects.equals(movie.getName(), action.getMovie())) {
                            state.movies = new ArrayList<>();
                            state.movies.add(movie);

                            currentMovie = movie;

                            state.page = PageFactory.createPage(pageType);

                            return SUCCESS_SEE_DETAILS;
                        }
                    }
                    currentMovie = null;

                    return MOVIE_NOT_FOUND;

                } else {
                    state.page = PageFactory.createPage(pageType);

                    return SUCCESS_PAGE_CHANGE;
                }
            }
        }

        /* Page not found */
//        System.out.println("Page(" + pageName + ") not found");
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