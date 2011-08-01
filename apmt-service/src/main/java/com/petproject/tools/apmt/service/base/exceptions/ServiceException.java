package com.petproject.tools.apmt.service.base.exceptions;

public class ServiceException extends RuntimeException{

	public ServiceException(String message) {
		super(message, null);
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}