package com.aviation.airport.detailsfinder.controller;


import com.aviation.airport.detailsfinder.bean.RequestEntity;
import com.aviation.airport.detailsfinder.bean.ResultEntity;
import com.aviation.airport.detailsfinder.service.AirportWithRunwaysPerCountryService;
import com.aviation.airport.detailsfinder.service.CountriesWithAirportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * This is controller class that takes care of mapping the incoming request
 */
@Api(value="onlinestore", description="This API serves airport and runways retrival functionality")
@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private CountriesWithAirportService countriesWithAirportService;

    @Autowired
    private AirportWithRunwaysPerCountryService airportWithRunwaysPerCountryService;

    /**
     * This method fetches all runways in the airports of a given country
     * @param requestEntity contains input data of either country code or name
     * @return ResponseEntity<ResultEntity> based on the received country code or name
     */
    @ApiOperation(value = "View a the list of airport and runways for the asked country",response = ResultEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list")
      })
    @PostMapping(path = "/fetchairportandrunwaysforcountry", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResultEntity> fetchAirportAndRunwaysForCountry(@RequestBody RequestEntity requestEntity) {

        return airportWithRunwaysPerCountryService.getRunwaysAndAirportsForGivenCountrySpec(requestEntity);
    }

    /**
     * This method gets the list of top 10 countries with highest number of airports
     * @return ResponseEntity<Map<String, ResultEntity>> ,this map holds the
     * country as its Key and details of their respective airports lists
     */
    @ApiOperation(value = "This operation retrives the list of top 10 countries with highest number of airports",response = ResultEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    @GetMapping(value = "/gettoptencountrieswithairports")
    public ResponseEntity<Map<String, ResultEntity>> getTopTenCountrieswithAirports() {
        return countriesWithAirportService.sortTopTenCountrieswithAirports();

    }

}
