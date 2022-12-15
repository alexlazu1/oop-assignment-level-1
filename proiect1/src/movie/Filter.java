package movie;

public class Filter {
    private FilterSort sort;
    private FilterContains contains;

    public Filter() {
    }

    public FilterSort getSort() {
        return sort;
    }

    public void setSort(FilterSort sort) {
        this.sort = sort;
    }

    public FilterContains getContains() {
        return contains;
    }

    public void setContains(FilterContains contains) {
        this.contains = contains;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "sort=" + sort +
                ", contains=" + contains +
                '}';
    }
}
