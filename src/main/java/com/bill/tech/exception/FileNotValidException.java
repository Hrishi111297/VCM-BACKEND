package com.bill.tech.exception;


public class FileNotValidException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1486383388987024913L;

	public FileNotValidException(String message) {
        super(message);
    }
}
