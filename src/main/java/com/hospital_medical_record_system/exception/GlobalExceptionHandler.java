package com.hospital_medical_record_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hospital_medical_record_system.dto.ResponseStructureDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handling IdNotFoundException
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleIdNotFoundException(IdNotFoundException exception) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        response.setData("Failure");
        return new ResponseEntity<ResponseStructureDto<String>>(response, HttpStatus.NOT_FOUND);
    }

    // Handling AppointmentException
    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleAppointmentException(AppointmentException exception) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setData("Failure");
        return new ResponseEntity<ResponseStructureDto<String>>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Handling MedicalException
    @ExceptionHandler(MedicalRecordException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleMedicalRecordException(MedicalRecordException exception) {
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setData("Failure");
        return new ResponseEntity<ResponseStructureDto<String>>(response, HttpStatus.BAD_REQUEST);
    }
    
    //Handling PrescriptionException
    @ExceptionHandler(PrescriptionException.class)
    public ResponseEntity<ResponseStructureDto<String>> handlePrescriptionException(PrescriptionException exception){
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        response.setData("Failure");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
 // Handling DuplicateDepartmentNameException
    @ExceptionHandler(DuplicateDepartmentNameException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleDuplicateDepartmentNameException(DuplicateDepartmentNameException exception) {
        
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        
        // Use 409 CONFLICT for duplicate data issues
        response.setStatusCode(HttpStatus.CONFLICT.value()); 
        response.setMessage(exception.getMessage());
        response.setData("Duplicate Entry Detected");
        
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    // Handling DuplicateDepartmentNameException
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ResponseStructureDto<String>> handleDuplicateEntryException(DuplicateEntryException exception) {
        
        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        
        // Match the internal code with the HTTP Status
        response.setStatusCode(HttpStatus.CONFLICT.value()); 
        response.setMessage(exception.getMessage());
        response.setData("Failure: Duplicate entry found in the database.");
        
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
