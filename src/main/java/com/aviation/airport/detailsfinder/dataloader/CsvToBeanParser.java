package com.aviation.airport.detailsfinder.dataloader;

import com.aviation.airport.detailsfinder.bean.CsvBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This is the Parser class which converts the parses the csv in the given path
 * to its Class Type as set in the Mapping Strategy.
 */
@Component
public class CsvToBeanParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvToBeanParser.class);

    /**
     * This method parses the csv to List<CsvBean> using OpenCSV
     *
     * @param path  path of the file to be parsed
     * @param clazz The class type to which the csv should parsed and type casted
     * @return List of type CsvBean
     * @throws IOException throws error if the file is not found in the specified path
     */
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
        LOGGER.info("Completed parsing of csv file to {} ", clazz);
        return csvToBeanList;
    }
}
