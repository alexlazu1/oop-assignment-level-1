package movie;

public class FilterSort {
    private String rating;
    private String duration;

    public FilterSort() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FilterSort{" +
                "rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
