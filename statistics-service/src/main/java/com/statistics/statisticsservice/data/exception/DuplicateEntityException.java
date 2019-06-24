package com.statistics.statisticsservice.data.exception;

public class DuplicateEntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8120937288938216892L;

	public DuplicateEntityException(String message) {
        super(message);
    }
}
