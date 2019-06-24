package com.stock.service.stockservice.exception;

import java.util.Date;

public class ExceptionResponse {
  private String timestamp;
  private String message;
  private String details;


  public ExceptionResponse(String timestamp, String message, String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }
}
