package com.autocomplete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexBuilder {
    private final String csvFile;
    private final int columnId;
    private final CsvParser csvParser;

    public IndexBuilder(String csvFile, int columnId) {
        this.csvFile = csvFile;
        this.columnId = columnId - 1;
        this.csvParser = new CsvParser();
    }

    public Map<String, List<Integer>> buildIndex() throws IOException {
        Map<String, List<Integer>> index = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = csvParser.parseLine(line);
                if (fields.length <= columnId) continue;

                String key = fields[columnId].toLowerCase();
                int rowNumber = Integer.parseInt(fields[0]);

                index.computeIfAbsent(key, k -> new ArrayList<>()).add(rowNumber);
            }
        }

        return index;
    }
}
