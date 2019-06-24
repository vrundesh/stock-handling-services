package com.stock.service.stockservice.data.exception;

public class DuplicateRequestException extends  RuntimeException{

    public DuplicateRequestException(String message) {
        super(message);
    }
}
