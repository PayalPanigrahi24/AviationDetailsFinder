package com.aviation.airport.detailsfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.aviation.airport.detailsfinder.dataloader.InitializeAirports.initializeAirportMap;
import static com.aviation.airport.detailsfinder.dataloader.InitializeCountries.initializeCountryList;
import static com.aviation.airport.detailsfinder.dataloader.InitializeRunways.initializeRunwayMap;
import static com.aviation.airport.detailsfinder.dataloader.InitializeUpdateResultEntity.initializeResultEntity;

/**
 * This is the bootstrap class of the spring boot application
 */
@SpringBootApplication
public class DetailsFinderApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsFinderApplication.class);

    /**
     * This is entry point to the application from where
     * the compilation starts.
     *
     * @param args an array of characters passed
     */
    public static void main(String[] args) {

        SpringApplication.run(DetailsFinderApplication.class, args);
        loadAllData();
        LOGGER.info("Application has loaded all data from the CSV");
    }

    /**
     * This method loads all the data from the csv file
     * ans store in static memory location
     */
    private static void loadAllData() {
        initializeAirportMap();
        initializeCountryList();
        initializeRunwayMap();
        initializeResultEntity();
    }

}
