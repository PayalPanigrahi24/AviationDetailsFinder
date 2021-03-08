package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.aviation.airport.detailsfinder.bean.Runways;
import com.aviation.airport.detailsfinder.exception.CsvParserException;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
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
public class InitializeRunways {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeRunways.class);

    public static Map<Integer, List<Runways>> runwayMap = new HashMap<>();

    static {
        try {
            initializeRunwayMap();
        } catch (CsvParserException e) {
            LOGGER.error("File could not be parsed due to {} ", e.getMessage());
        }
    }

    /**
     * This method loads the csv of runways to a list of runways
     */
    public static void initializeRunwayMap() throws CsvParserException {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/runways.csv").toURI());
            List<CsvBean> listOfRunwayBean = CsvToBeanParser.parseCsvToObject(path, Runways.class);
            loadRunwayMap(listOfRunwayBean);
            LOGGER.info("Loaded data for Countries Map");
        } catch (URISyntaxException | IOException e) {
            throw new CsvParserException("Error while parsing the runways csv");

        }
    }

    /**
     * This method loads the list of Runways to the static Hashmap
     *
     * @param listOfRunways listOfRunways
     */
    private static void loadRunwayMap(List<CsvBean> listOfRunways) {
        listOfRunways.stream()
                .forEach(n -> {
                            Runways s = (Runways) n;
                            if (runwayMap.containsKey(s.getAirport_ref())) {
                                runwayMap.get(s.getAirport_ref()).add(s);
                            } else {
                                List<Runways> listOfRunwaysPerAirport = new ArrayList<Runways>();
                                listOfRunwaysPerAirport.add(s);
                                runwayMap.put(s.getAirport_ref(), listOfRunwaysPerAirport);
                            }
                        }
                );

    }
}
