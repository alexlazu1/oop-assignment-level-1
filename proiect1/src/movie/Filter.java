package movie;

public final class Filter {
    private FilterSort sort;
    private FilterContains contains;

    public Filter() {
    }

    public FilterSort getSort() {
        return sort;
    }

    public void setSort(final FilterSort sort) {
        this.sort = sort;
    }

    public FilterContains getContains() {
        return contains;
    }

    public void setContains(final FilterContains contains) {
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
