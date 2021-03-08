package com.aviation.airport.detailsfinder.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * This is the API error class that holds the error message
 * and http status incase of invalid inputs.
 */
@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String errormessage;

}
