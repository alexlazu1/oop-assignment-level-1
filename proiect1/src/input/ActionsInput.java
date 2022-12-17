package input;

import movie.Filter;

public final class ActionsInput {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private Filter filters;
    private String movie; // the name of the movie;
    private String startsWith;
    private String count;
    private int rate;


    public ActionsInput() {

    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ActionsInput{" +
                "type='" + type + '\'' +
                ", page='" + page + '\'' +
                ", credentials=" + credentials +
                ", feature='" + feature + '\'' +
                ", filters=" + filters +
                ", movie='" + movie + '\'' +
                ", startsWith='" + startsWith + '\'' +
                ", count='" + count + '\'' +
                ", rate=" + rate +
                '}';
    }
}
