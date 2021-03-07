package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.aviation.airport.detailsfinder.bean.Runways;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class InitializeRunways {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeRunways.class);

    public static Map<Integer, List<Runways>> runwayMap = new HashMap<>();

    private InitializeRunways() {

    }

    static {
        initializeRunwayMap();
    }


    public static void initializeRunwayMap() {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("files/runways.csv").toURI());
            List<CsvBean> listOfRunwayBean = CsvToBeanParser.parseCsvToObject(path, Runways.class);
            loadRunwayMap(listOfRunwayBean);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

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
