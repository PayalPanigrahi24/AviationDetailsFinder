package com.aviation.airport.detailsfinder.service;

import com.aviation.airport.detailsfinder.bean.RequestEntity;
import com.aviation.airport.detailsfinder.bean.ResultEntity;
import com.aviation.airport.detailsfinder.dataloader.InitializeCountries;
import com.aviation.airport.detailsfinder.dataloader.InitializeUpdateResultEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class AirportWithRunwaysPerCountryService {

    private static ResultEntity res = null;

    /**
     * This method returns the details of runways and airports for a given country code or name
     * else returns an error message to enter a valid input
     *
     * @param requestEntity input specifications received
     * @return ResponseEntity<ResultEntity> based on the input parameters
     */
    public ResponseEntity<ResultEntity> getRunwaysAndAirportsForGivenCountrySpec(RequestEntity requestEntity) {

        // Checks If Country code is valid and is not null
        if (StringUtils.isNotBlank(requestEntity.getCountrycode()) &&
                InitializeUpdateResultEntity.resultEntityToCodeMap.containsKey
                        (requestEntity.getCountrycode().trim().toUpperCase())) {
            return ResponseEntity.ok(InitializeUpdateResultEntity.resultEntityToCodeMap.get(requestEntity.getCountrycode().trim().toUpperCase()));
        }

        //Checks If Country name is valid and is not null
        else if (StringUtils.isNotBlank(requestEntity.getCountryname())) {

            if (InitializeCountries.countryNameToCountryCodeMap.containsKey
                    (requestEntity.getCountryname().trim().toUpperCase())) {
                return ResponseEntity.ok(InitializeUpdateResultEntity.resultEntityToCodeMap.get(InitializeCountries.countryNameToCountryCodeMap.
                        get(requestEntity.getCountryname().trim().
                                toUpperCase())));
            }

            //Checks If the input is a fuzzy country name
            else {
                return getRunwaysAndAirportsForFuzzyCountry(requestEntity);
            }

        } else {
            ResultEntity ent = new ResultEntity();
            ent.setFinalmessage("Please enter a valid Country code or name");
            return ResponseEntity.ok(ent);
        }
    }

    /**
     * This method finds if the entered fuzzy country name holds any valid country or not in the map
     *
     * @param requestEntity input parameters for the API
     * @return ResponseEntity<ResultEntity> returns possible exisiting countries else returns a valid error message
     */
    public ResponseEntity<ResultEntity> getRunwaysAndAirportsForFuzzyCountry(RequestEntity requestEntity) {

        Map<String, String> nilst = InitializeCountries.countryNameToCountryCodeMap.entrySet()
                .stream()
                .filter(e -> e.getKey().contains(requestEntity.getCountryname().trim().toUpperCase()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        //If there are multiple countries for the input fuzzy country name
        if (nilst.size() > 1) {
            ResultEntity ent = new ResultEntity();
            ent.setFinalmessage("Possible Country names with the given input are :" + nilst.keySet());
            return ResponseEntity.ok(ent);
        }
        //If there is only a single country for the input fuzzy country name
        else if (nilst.size() == 1) {
            nilst.entrySet().forEach(
                    s -> {
                        res = InitializeUpdateResultEntity.resultEntityToCodeMap
                                .get(InitializeCountries.countryNameToCountryCodeMap
                                        .get(s.getKey().trim().toUpperCase()
                                        ));

                    }
            );
            return ResponseEntity.ok(res);
        } else {
            ResultEntity ent = new ResultEntity();
            ent.setFinalmessage("Please enter a valid Country code or name");
            return ResponseEntity.ok(ent);
        }

    }
}
