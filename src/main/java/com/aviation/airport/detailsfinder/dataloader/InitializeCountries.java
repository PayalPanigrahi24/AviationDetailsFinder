package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.Countries;
import com.aviation.airport.detailsfinder.bean.CsvBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitializeCountries {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeCountries.class);

    public static Map<String, String> countryNameToCountryCodeMap = new HashMap<>();
    public static Map<String, Countries> countryCodeToCountryMap = new HashMap<>();
    public static List<CsvBean> listOfCountries = new ArrayList<>();

    private InitializeCountries() {

    }

    /**
     * This method loads the csv of countries to a list of countries
     */
    public static void initializeCountryList() {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/countries.csv").toURI());
            listOfCountries = CsvToBeanParser.parseCsvToObject(path, Countries.class);
            loadCountryNameToCountryCodeMap(listOfCountries);
            LOGGER.info("Loaded data for Countries Map");
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("File not found to csv reader {} ", e.getMessage());
        }
    }

    /**
     * This method loads the list of Countries to the static Hashmap
     * @param listOfCountries listOfCountries
     */
    private static void loadCountryNameToCountryCodeMap(List<CsvBean> listOfCountries) {
        listOfCountries.stream().forEach(
                n -> {
                    Countries s = (Countries) n;
                    countryNameToCountryCodeMap.put(s.getName().trim().toUpperCase(), s.getCode());
                }
        );


    }


}
