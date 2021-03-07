package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.Airports;
import com.aviation.airport.detailsfinder.bean.Countries;
import com.aviation.airport.detailsfinder.bean.ResultEntity;
import com.aviation.airport.detailsfinder.bean.Runways;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InitializeResultEntity {


    public static Map<String, ResultEntity> resultToCodeMap = new HashMap<>();
    public static Map<String, ResultEntity> resultToNameMap = new HashMap<>();

    public static void  initializeResultEntity() {
        List<Countries> countriesList = InitializeCountries.listOfCountries;
        Map<String, List<Airports>> airportMap = InitializeAirports.airportMap;
        Map<String, List<Runways>> runwayMap = InitializeRunways.runwayMap;

        countriesList.stream().skip(1).forEach(
                s -> {
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
                    resultToCodeMap.put(s.getCode(), resultEntity);
                    resultToNameMap.put(s.getName(), resultEntity);
                }
        );
    }


}
