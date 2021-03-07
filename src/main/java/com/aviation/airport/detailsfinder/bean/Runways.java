package com.aviation.airport.detailsfinder.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Runways extends CsvBean {

    @CsvBindByPosition(position=0)
    private int id;
    @CsvBindByPosition(position=1)
    private int airport_ref;
    @CsvBindByPosition(position=2)
    private String airport_ident;
    @CsvBindByPosition(position=3)
    private int length_ft;
    @CsvBindByPosition(position=4)
    private int width_ft;
    @CsvBindByPosition(position=5)
    private String surface;
    @CsvBindByPosition(position=6)
    private int lighted;
    @CsvBindByPosition(position=7)
    private int closed;
    @CsvBindByPosition(position=8)
    private String le_ident;
    @CsvBindByPosition(position=9)
    private double le_latitude_deg;
    @CsvBindByPosition(position=10)
    private double le_longitude_deg;
    @CsvBindByPosition(position=11)
    private int le_elevation_ft;
    @CsvBindByPosition(position=12)
    private double le_heading_degT;
    @CsvBindByPosition(position=13)
    private int le_displaced_threshold_ft;
    @CsvBindByPosition(position=14)
    private String he_ident;
    @CsvBindByPosition(position=15)
    private double he_latitude_deg;
    @CsvBindByPosition(position=16)
    private double he_longitude_deg;
    @CsvBindByPosition(position=17)
    private int he_elevation_ft;
    @CsvBindByPosition(position=18)
    private double he_heading_degT;
    @CsvBindByPosition(position=19)
    private int he_displaced_threshold_ft;
}
