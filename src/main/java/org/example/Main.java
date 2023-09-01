package org.example;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("src/City.csv"))
                .withCSVParser(parser)
                .build()) {

            var allRows = reader.readAll();
            List<City> cities = allRows.stream()
                    .map(Main::createCityFromCSVRow)
                    .sorted(Comparator.comparing((City city) -> city.getDistrict().toLowerCase())
                            .thenComparing((City city) -> city.getName().toLowerCase()))
                    .collect(Collectors.toList());

            cities.forEach(System.out::println);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    static City createCityFromCSVRow(String[] rowData) {
        String name = rowData[1];
        String region = rowData[2];
        String district = rowData[3];
        String population = rowData[4];
        String foundation = rowData[5];

        return new City(name, region, district, population, foundation);
    }
}