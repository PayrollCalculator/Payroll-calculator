package com.payroll.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Apache Commons CSV parser implementation.
 */
public class ApacheCommonsCsvParser implements CsvParser {
    private static final Logger logger = LoggerFactory.getLogger(ApacheCommonsCsvParser.class);
    private final char delimiter;
    private final FileReader fileReader;

    /**
     * Constructs a CSV parser with the default delimiter (semicolon).
     */
    public ApacheCommonsCsvParser(FileReader fileReader) {
        this(fileReader, ';');
    }

    /**
     * Constructs a CSV parser with a specified delimiter.
     *
     * @param delimiter The character to use as delimiter
     */
    public ApacheCommonsCsvParser(FileReader fileReader, char delimiter) {
        this.fileReader = fileReader;
        this.delimiter = delimiter;
    }

    @Override
    public List<String[]> parse(String path) {
        List<String> lines = fileReader.readAllLines(path);
        List<String[]> result = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            logger.warn("Empty or null lines provided for parsing");
            return result;
        }

        // Join lines into a single string with newlines
        String content = String.join("\n", lines);

        CSVFormat csvFormat = CSVFormat.Builder.create()
            .setDelimiter(delimiter)
            .setTrim(true)
            .setIgnoreEmptyLines(true)
            .build();

        try (StringReader reader = new StringReader(content);
             CSVParser csvParser = new CSVParser(reader, csvFormat)) {

            for (CSVRecord record : csvParser) {
                String[] row = new String[record.size()];
                for (int i = 0; i < record.size(); i++) {
                    String value = record.get(i);
                    row[i] = value.isEmpty() ? null : value;
                }
                result.add(row);
            }
        } catch (IOException e) {
            logger.error("Error parsing CSV content", e);
        }

        return result;
    }

    @Override
    public List<Map<String, String>> parseWithHeaders(String path) {
        List<String> lines = fileReader.readAllLines(path);
        List<Map<String, String>> result = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            logger.warn("Empty or null lines provided for parsing");
            return result;
        }

        // Join lines into a single string with newlines
        String content = String.join("\n", lines);

        CSVFormat csvFormat = CSVFormat.Builder.create()
            .setDelimiter(delimiter)
            .setTrim(true)
            .setIgnoreEmptyLines(true)
            .setHeader()
            .build();

        try (StringReader reader = new StringReader(content);
             CSVParser csvParser = new CSVParser(reader, csvFormat)) {

            for (CSVRecord record : csvParser) {
                Map<String, String> row = new HashMap<>();
                for (String header : csvParser.getHeaderNames()) {
                    String value = record.get(header);
                    row.put(header, value.isEmpty() ? null : value);
                }
                result.add(row);
            }
        } catch (IOException e) {
            logger.error("Error parsing CSV content", e);
        }

        return result;
    }
}