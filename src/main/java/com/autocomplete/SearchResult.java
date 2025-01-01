package com.autocomplete;

import java.util.List;

public final class SearchResult {
    private final String search;
    private final List<Integer> result;
    private final long time;

    public SearchResult(String search, List<Integer> result, long time) {
        this.search = search;
        this.result = result;
        this.time = time;
    }

    public String getSearch() {
        return search;
    }

    public List<Integer> getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }
}