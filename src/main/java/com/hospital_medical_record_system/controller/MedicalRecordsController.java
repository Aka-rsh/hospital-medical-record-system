package com.hospital_medical_record_system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.service.MedicalRecordsService;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordsController {

	@Autowired
	private MedicalRecordsService medicalRecordsService;

	// Create a new medical record
	@PostMapping("/create")
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> createRecord(@RequestBody MedicalRecords record) {
		return medicalRecordsService.createRecord(record);
	}

	// Fetch all medical records
	@GetMapping
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchAllRecords() {
		return medicalRecordsService.fetchAllRecords();
	}

	// Fetch record by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> fetchRecordById(@PathVariable Long id) {
		return medicalRecordsService.fetchRecordById(id);
	}

	// Fetch records by patient
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByPatient(
	        @PathVariable Long patientId) {
	    return medicalRecordsService.fetchRecordByPatient(patientId);
	}

	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByDoctor(
	        @PathVariable Long doctorId) {
	    return medicalRecordsService.fetchRecordByDoctor(doctorId); 
	}

	// Fetch records by appointment
	@GetMapping("/by-appointment/{appointmentId}")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByAppointment(
	        @PathVariable Long appointmentId) { // PathVariable asaan hai
	    return medicalRecordsService.fetchRecordByAppointment(appointmentId);
	}

	// Fetch records by visit date
	@GetMapping("/by-visit-date")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByVisitDate(
	        @RequestParam String visitDate) {
	    String formattedDate = visitDate.trim().replace(" ", "T");
	    LocalDateTime dt = LocalDateTime.parse(formattedDate);
	    return medicalRecordsService.fetchRecordByVisitDate(dt);
	}
}
