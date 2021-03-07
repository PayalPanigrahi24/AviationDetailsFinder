package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.Countries;
import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.aviation.airport.detailsfinder.bean.Runways;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InitializeCountries {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeCountries.class);

    public static Map<String, String> countryNameToCountryCodeMap = new HashMap<>();
    public static Map<String, Countries> countryCodeToCountryMap = new HashMap<>();
    public static List<CsvBean> listOfCountries = new ArrayList<>();

    private InitializeCountries() {

    }

    static {
        initializeCountryList();
    }


    public static void initializeCountryList() {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/countries.csv").toURI());
            listOfCountries = CsvToBeanParser.parseCsvToObject(path, Countries.class);
            loadCountryNameToCountryCodeMap(listOfCountries);
       } catch (URISyntaxException | IOException e) {
            LOGGER.error("File not found to csv reader {} ", e.getMessage());
        }
    }

    private static void loadCountryNameToCountryCodeMap(List<CsvBean> listOfCountries) {
        listOfCountries.stream().forEach(
                n -> {
                    Countries s = (Countries) n;
                    countryNameToCountryCodeMap.put(s.getName().trim().toUpperCase(), s.getCode());
                }
        );


    }


}
