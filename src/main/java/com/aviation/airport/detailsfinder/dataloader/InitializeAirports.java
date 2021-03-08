package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.Airports;
import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.aviation.airport.detailsfinder.exception.CsvParserException;
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
public class InitializeAirports {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeAirports.class);

    public static Map<String, List<Airports>> airportMap = new HashMap<>();

    static {
        try {
            initializeAirportMap();
        } catch (CsvParserException e) {
            LOGGER.error("File could not be parsed due to {} ", e.getMessage());
        }
    }

    /**
     * This method loads the csv of airport to a list
     */
    public static void initializeAirportMap() throws CsvParserException {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/airports.csv").toURI());
            List<CsvBean> listOfAirports = CsvToBeanParser.parseCsvToObject(path, Airports.class);
            loadAirportMap(listOfAirports);
            LOGGER.info("Loaded data for Airport Map");
        } catch (URISyntaxException | IOException e) {
            throw new CsvParserException("Error while parsing the airport csv");

        }
    }

    /**
     * This method loads the list of airport to the static Hashmap
     */
    private static void loadAirportMap(List<CsvBean> listOfAirports) {
        listOfAirports.stream().forEach(
                n -> {
                    Airports s = (Airports) n;
                    if (airportMap.containsKey(s.getIso_country())) {
                        airportMap.get(s.getIso_country()).add(s);
                    } else {
                        List<Airports> listOfAirportsPerCountry = new ArrayList<Airports>();
                        listOfAirportsPerCountry.add(s);
                        airportMap.put(s.getIso_country(), listOfAirportsPerCountry);
                    }
                }
        );

    }
}
