package com.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ',') {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else if (c != '\"') {
                currentField.append(c);
            }
        }

        fields.add(currentField.toString().trim());
        return fields.toArray(new String[0]);
    }
}
