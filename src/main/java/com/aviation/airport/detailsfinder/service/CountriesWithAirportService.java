package com.aviation.airport.detailsfinder.service;

import com.aviation.airport.detailsfinder.bean.ResultEntity;
import com.aviation.airport.detailsfinder.dataloader.InitializeUpdateResultEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class holds the business meaning of the implemented controller APIS
 */
@Service
public class CountriesWithAirportService {

    /**
     * This method gets the list of top 10 countries with highest number of airports
     *
     * @return ResponseEntity<Map < String, ResultEntity>> ,where country is stored as the map
     * and the details of their airport is stored as value in the map.
     */
    public ResponseEntity<Map<String, ResultEntity>> sortTopTenCountrieswithAirports() {
        Map<String, ResultEntity> sortedResult = InitializeUpdateResultEntity.resultEntityToCodeMap
                .entrySet()
                .stream().sorted((e1, e2) ->
                        ((Integer) (e2.getValue().getAirportsList() == null ? 0 : e2.getValue().getAirportsList().size()))
                                .compareTo(e1.getValue().getAirportsList() == null ? 0 : e1.getValue().getAirportsList().size()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        return ResponseEntity.ok(sortedResult);
    }
}
