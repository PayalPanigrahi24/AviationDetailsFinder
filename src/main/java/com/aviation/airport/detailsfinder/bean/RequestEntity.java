package com.aviation.airport.detailsfinder.bean;

import lombok.Data;

/**
 * Class that holds all the parameters for an incoming API request
 */
@Data
public class RequestEntity {

    private String countrycode;

    private String countryname;
}
