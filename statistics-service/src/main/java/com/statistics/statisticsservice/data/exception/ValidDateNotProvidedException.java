package com.statistics.statisticsservice.data.exception;

import org.springframework.http.HttpStatus;

public class ValidDateNotProvidedException extends RuntimeException  {


    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public ValidDateNotProvidedException(HttpStatus httpStatus, String message) {

        super(message);
        this.httpStatus = httpStatus;
    }
}
