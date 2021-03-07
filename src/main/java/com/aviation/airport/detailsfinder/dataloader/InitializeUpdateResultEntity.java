package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitializeUpdateResultEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeUpdateResultEntity.class);

    public static Map<String, ResultEntity> resultEntityToCodeMap = new HashMap<>();
    public static Map<String, ResultEntity> resultEntityToNameMap = new HashMap<>();

    static{
        initializeResultEntity();
        LOGGER.info("Application has loaded all data from the CSV");
    }

    /**
     * This method initializes the result entity map
     * It holds the data per every given country to airport and runways list
     */
    public static void initializeResultEntity() {
        List<CsvBean> countriesList = InitializeCountries.listOfCountries;
        Map<String, List<Airports>> airportMap = InitializeAirports.airportMap;
        Map<Integer, List<Runways>> runwayMap = InitializeRunways.runwayMap;

        countriesList.stream().forEach(
                n -> {
                    Countries s = (Countries) n;
                    ResultEntity resultEntity = new ResultEntity();
                    resultEntity.setCode(s.getCode());
                    resultEntity.setName(s.getName());
                    List<Airports> listAirportsForCountry = airportMap.get(s.getCode());
                    if (null != listAirportsForCountry) {

                        listAirportsForCountry.stream().forEach(
                                m -> {
                                    List<Runways> listRunwaysForAirports = runwayMap.get(m.getId());
                                    if (null != listRunwaysForAirports) {
                                        m.setRunwaysList(listRunwaysForAirports);
                                    }
                                }
                        );
                    }
                    resultEntity.setAirportsList(listAirportsForCountry);
                    resultEntityToCodeMap.put(s.getCode(), resultEntity);
                    resultEntityToNameMap.put(s.getName(), resultEntity);
                }
        );
    }


}
