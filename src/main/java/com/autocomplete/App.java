package com.autocomplete;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        if (args.length < 8) {
            System.out.println("Use the format \"java -jar autocomplete.jar --data <csv_file> --indexed-column-id <column_id> --input-file <input_file> --output-file <output_file>\"");
            return;
        }

        String csvFile = null;
        int columnId = -1;
        String inputFile = null;
        String outputFile = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--data":
                    csvFile = args[++i];
                    break;
                case "--indexed-column-id":
                    try {
                        columnId = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("indexed-column-id must be an integer");
                        return;
                    }
                    break;
                case "--input-file":
                    inputFile = args[++i];
                    break;
                case "--output-file":
                    outputFile = args[++i];
                    break;
                default:
                    System.out.println("Unknown argument " + args[i]);
                    return;
            }
        }

        if (csvFile == null) {
            System.out.println("The data file was not passed as an argument");
            return;
        }
        if (columnId < 1) {
            System.out.println("The indexed-column-id cannot be less than 1");
            return;
        }
        if (inputFile == null) {
            System.out.println("The input-file file was not passed as an argument");
            return;
        }
        if (outputFile == null) {
            System.out.println("The output-file file was not passed as an argument");
            return;
        }
        if (!new File(csvFile).exists()) {
            System.out.println("The data file " + csvFile + " does not exist");
            return;
        }
        if (!new File(inputFile).exists()) {
            System.out.println("The input file " + inputFile + " does not exist");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(new ObjectMapper().writeValueAsString(new Searcher(csvFile, columnId).search(startTime, inputFile)));
        }
        System.out.println("Search completed. Results written to " + outputFile);
    }
}