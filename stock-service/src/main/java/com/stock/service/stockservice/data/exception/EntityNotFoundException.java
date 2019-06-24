package com.stock.service.stockservice.data.exception;

public class EntityNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4560255117151712383L;

	public EntityNotFoundException(String message) {
        super(message);
    }
}
