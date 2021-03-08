package com.aviation.airport.detailsfinder.exception;

/**
 * This is the custom exception class that throws exception
 * when the details entered are invalid
 */
public class CsvParserException extends Exception {
    /**
     * Constructs a new exception with the specified detail
     *
     * @param message the detailed error message
     */
    public CsvParserException(String message) {
        super(message);
    }
}
