package com.bill.tech.exception;

import lombok.experimental.StandardException;

@StandardException
public class ResourceNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3545842558215195360L;
protected String resource;
protected String fieldname;
protected String fieldvalue;

	public ResourceNotFound(String resource, String fieldname, String fieldvalue) {
		super(String.format("%s not found for %s with %s ", resource, fieldname, fieldvalue));
		this.resource = resource;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}

	 public ResourceNotFound(String resource) {
		 super(String.format("%s not found.", resource)); 
	        this.resource = resource;
	    
	    }

}
