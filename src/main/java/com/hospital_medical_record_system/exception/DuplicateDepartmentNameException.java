package com.hospital_medical_record_system.exception;

public class DuplicateDepartmentNameException extends RuntimeException {
    public DuplicateDepartmentNameException(String message) {
        super(message);
    }
}
