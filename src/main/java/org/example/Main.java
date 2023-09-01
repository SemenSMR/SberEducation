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
//   Сортировка !
//                    .sorted(Comparator.comparing((City city) -> city.getDistrict().toLowerCase())
//                            .thenComparing((City city) -> city.getName().toLowerCase()))
                    .collect(Collectors.toList());
 //                       cities.forEach(System.out::println);
                        maxNumberOfResident(cities);
                        countCities(cities);

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
    public static void maxNumberOfResident(List<City> cities){
        int maxPopulationIndex = -1;
        int maxPopulation = Integer.MIN_VALUE;
        for (int i = 0; i < cities.size(); i++) {
            int population = Integer.parseInt(cities.get(i).getPopulation());
            if(population > maxPopulation){
                maxPopulation = population;
                maxPopulationIndex = i;
            }
        }

        if (maxPopulationIndex != -1){
            System.out.printf("[%d] = %,d%n", maxPopulationIndex,maxPopulation);
        } else {
            System.out.println("Список городов пуст");
        }
    }
    public static void countCities(List<City> cities){
        Map<String,Integer> regionCount = new HashMap<>();
        for (City city : cities){
            String region = city.getRegion();
            if(regionCount.containsKey(region)){
                regionCount.put(region, regionCount.get(region) + 1);
            } else {
                regionCount.put(region,1);
            }

        }
            for(Map.Entry<String, Integer> entry : regionCount.entrySet()){
                System.out.printf("%s %d%n", entry.getKey(),entry.getValue());
            }
    }
}