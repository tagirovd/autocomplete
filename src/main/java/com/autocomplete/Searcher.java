package com.autocomplete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Searcher {
    private final Map<String, List<Integer>> index;

    public Searcher(String csvFile, int columnId) throws IOException {
        this.index = new IndexBuilder(csvFile, columnId).buildIndex();
    }

    public SearchResults search(long startTime, String inputFile) throws IOException {
        List<SearchResult> results = new ArrayList<>();
        long initTime = System.currentTimeMillis() - startTime;

        try (BufferedReader inputReader = new BufferedReader(new FileReader(inputFile))) {
            String searchLine;
            while ((searchLine = inputReader.readLine()) != null) {
                if (searchLine.trim().isEmpty()) continue;

                long searchStartTime = System.currentTimeMillis();
                List<Integer> resultRowNumbers = searchInIndex(searchLine);
                long searchTime = System.currentTimeMillis() - searchStartTime;

                results.add(new SearchResult(searchLine, resultRowNumbers, searchTime));
            }
        }

        return new SearchResults(initTime, results);
    }

    private List<Integer> searchInIndex(String searchLine) {
        List<Integer> resultRowNumbers = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : index.entrySet()) {
            if (entry.getKey().startsWith(searchLine.toLowerCase())) {
                resultRowNumbers.addAll(entry.getValue());
            }
        }

        return resultRowNumbers;
    }
}
