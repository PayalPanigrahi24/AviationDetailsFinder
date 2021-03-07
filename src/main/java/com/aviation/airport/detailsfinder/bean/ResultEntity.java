package com.aviation.airport.detailsfinder.bean;

import lombok.Data;

import java.util.List;

/**
 * Response Class that holds all the return parameters for an incoming API request
 */
@Data
public class ResultEntity {

    private String code;
    private String name;
    private List<Airports> airportsList;
    private String finalmessage;

}
