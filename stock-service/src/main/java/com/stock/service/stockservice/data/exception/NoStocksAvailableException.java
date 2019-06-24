package com.stock.service.stockservice.data.exception;

public class NoStocksAvailableException extends RuntimeException {

    public NoStocksAvailableException(String message) {
        super(message);
    }
}
