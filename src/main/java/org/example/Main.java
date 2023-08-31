package org.example;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("src/City.csv"))
                .withCSVParser(parser)
                .build()) {

            var allRows = reader.readAll();
            for (String[] row : allRows) {
                List<String> rowData = Arrays.asList(row);
                City city = createCityFromCSVRow(rowData);
                System.out.println(city);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    static City createCityFromCSVRow(List<String> rowData) {
        String name = rowData.get(1);
        String region = rowData.get(2);
        String district = rowData.get(3);
        String population = rowData.get(4);
        String foundation = rowData.get(5);

        return new City(name, region, district, population, foundation);
    }
}