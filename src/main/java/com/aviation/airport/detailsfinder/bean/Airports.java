package com.aviation.airport.detailsfinder.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;

/**
 * Class that holds all the parameters of the airports
 */
@Data
public class Airports extends CsvBean{
    @CsvBindByPosition(position=0)
    private int id;
    @CsvBindByPosition(position=1)
    private String ident;
    @CsvBindByPosition(position=2)
    private String type;
    @CsvBindByPosition(position=3)
    private String name;
    @CsvBindByPosition(position=4)
    private double latitude_deg;
    @CsvBindByPosition(position=5)
    private double longitude_deg;
    @CsvBindByPosition(position=6)
    private double elevation_ft;
    @CsvBindByPosition(position=7)
    private String continent;
    @CsvBindByPosition(position=8)
    private String iso_country;
    @CsvBindByPosition(position=9)
    private String iso_region;
    @CsvBindByPosition(position=10)
    private String municipality;
    @CsvBindByPosition(position=11)
    private String scheduled_service;
    @CsvBindByPosition(position=12)
    private String gps_code;
    @CsvBindByPosition(position=13)
    private String iata_code;
    @CsvBindByPosition(position=14)
    private String local_code;
    @CsvBindByPosition(position=15)
    private String home_link;
    @CsvBindByPosition(position=16)
    private String wikipedia_link;
    @CsvBindByPosition(position=17)
    private String keywords;

    private List<Runways> runwaysList;
}
