package com.aviation.airport.detailsfinder.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

@Data
public class Countries extends CsvBean {

    @CsvBindByPosition(position=0)
    private String id;
    @CsvBindByPosition(position=1)
    private String code;
    @CsvBindByPosition(position=2)
    private String name;
    @CsvBindByPosition(position=3)
    private String continent;
    @CsvBindByPosition(position=4)
    private String wikipedia_link;
    @CsvBindByPosition(position=5)
    private String keywords;
}
