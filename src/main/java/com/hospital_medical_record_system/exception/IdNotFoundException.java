package com.hospital_medical_record_system.exception;

public class IdNotFoundException extends RuntimeException {
	public IdNotFoundException (String message){
		super(message);
	}
}
