package com.hospital_medical_record_system.exception;

public class DuplicateEntryException extends RuntimeException {
	public DuplicateEntryException(String message){
		super(message);
	}
}
