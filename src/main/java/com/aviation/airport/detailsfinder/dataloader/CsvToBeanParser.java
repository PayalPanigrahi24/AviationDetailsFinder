package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvToBeanParser {

    public static List<CsvBean> parseCsvToObject(Path path, Class clazz) throws IOException {
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(clazz);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withSeparator(',')
                .withMappingStrategy(mappingStrategy)
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .build();

        List<CsvBean> csvToBeanList = csvToBean.parse();
        reader.close();
        return csvToBeanList;
    }
}
