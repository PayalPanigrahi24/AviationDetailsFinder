package com.aviation.airport.detailsfinder.bean;

import lombok.Data;

import java.util.List;

@Data
public class ResultEntity {

    private String code;
    private String name;
    private List<Airports> airportsList;
    private String finalmessage;

}
