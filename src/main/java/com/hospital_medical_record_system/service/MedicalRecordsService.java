package com.hospital_medical_record_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.MedicalRecordsDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.exception.MedicalRecordException;
import com.hospital_medical_record_system.repository.AppointmentRepository;

@Service
public class MedicalRecordsService {

	@Autowired
	private MedicalRecordsDao medicalRecordsDao;

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Create a new medical record
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> createRecord(MedicalRecords record) {
		// 1. Extract IDs from the incoming record object
	    Long patientId = record.getPatient().getPatientId();
	    Long doctorId = record.getDoctor().getDoctorId();

	    // 2. Logic Trigger: Check if a COMPLETED appointment exists for this pair
	    Optional<Appointment> appointmentOpt = appointmentRepository.findCompletedAppointments(
	            patientId, 
	            doctorId
	        );


	    // 3. The "Else" Part: If no completed appointment is found, Block the flow
	    if (appointmentOpt.isEmpty()) {
	        throw new MedicalRecordException("Medical Record cannot be created. " +
	                                         "No COMPLETED appointment found for Patient ID: " + patientId + 
	                                         " with Doctor ID: " + doctorId);
	    }

	    // 4. Success Path: If the appointment is completed, link the data and save
	    record.setVisitDate(LocalDateTime.now()); // System-generated timestamp
		MedicalRecords saved = medicalRecordsDao.createRecord(record);

		ResponseStructureDto<MedicalRecords> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Medical record created successfully");
		response.setData(saved);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Fetch all records
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchAllRecords() {
	    List<MedicalRecords> records = medicalRecordsDao.fetchAllRecords();
	    ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
	    
	    if (!records.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Medical records fetched successfully");
	        response.setData(records);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No medical records found in database.");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch record by ID
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> fetchRecordById(Long id) {
		MedicalRecords record = medicalRecordsDao.fetchRecordById(id);
		ResponseStructureDto<MedicalRecords> response = new ResponseStructureDto<>();
		
		// In case DAO doesn't throw exception and returns null
		if (record != null) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Medical record fetched successfully");
			response.setData(record);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("Medical record with ID " + id + " not found.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// Fetch records by patient ID
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByPatient(Long patientId) {
	    // Patient patient = new Patient(); // Not strictly needed if DAO uses ID
	    // patient.setPatientId(patientId);
	    List<MedicalRecords> records = medicalRecordsDao.fetchRecordByPatient(patientId);
	    
	    ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
	    if (!records.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Records fetched successfully");
	        response.setData(records);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No medical records found for Patient ID: " + patientId);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}


	// Fetch records by doctor
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByDoctor(Long doctorId) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByDoctor(doctorId);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		
		if (!records.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Medical records fetched by doctor successfully");
			response.setData(records);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("No medical records found for Doctor ID: " + doctorId);
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// Fetch records by appointment
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByAppointment(
			Long appointmentId) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByAppointment(appointmentId);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		
		if (!records.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Medical records fetched by appointment successfully");
			response.setData(records);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("No medical records found for Appointment ID: " + appointmentId);
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// Fetch records by visit date
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByVisitDate(LocalDateTime visitDate) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByVisitDate(visitDate);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		
		if (!records.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Medical records fetched by visit date successfully");
			response.setData(records);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("No medical records found for the date: " + visitDate);
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}

