package com.autocomplete;

import java.util.List;

public final class SearchResults {
    private final long initTime;
    private final List<SearchResult> result;

    public SearchResults(long initTime, List<SearchResult> result) {
        this.result = result;
        this.initTime = initTime;
    }

    public List<SearchResult> getResult() {
        return result;
    }

    public long getInitTime() {
        return initTime;
    }
}