package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.Airports;
import com.aviation.airport.detailsfinder.bean.Countries;
import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
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

@Component
public class InitializeAirports {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeAirports.class);

    public static Map<String, List<Airports>> airportMap = new HashMap<>();

    static{
        initializeAirportMap();
    }

    public static void initializeAirportMap()  {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/airports.csv").toURI());
            List<CsvBean> listOfAirports = CsvToBeanParser.parseCsvToObject(path, Airports.class);
            loadAirportMap(listOfAirports);
        } catch (URISyntaxException  | IOException e) {
            LOGGER.error("File not found to csv reader {} ", e.getMessage());
        }
    }

    private static void loadAirportMap(List<CsvBean> listOfAirports) {
        listOfAirports.stream().forEach(
                n -> {
                   Airports s=(Airports)n;
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
