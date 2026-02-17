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
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;
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
	@PostMapping("/by-patient")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByPatient(
			@RequestBody Patient patient) {
		return medicalRecordsService.fetchRecordByPatient(patient);
	}

	// Fetch records by doctor
	@PostMapping("/by-doctor")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByDoctor(@RequestBody Doctor doctor) {
		return medicalRecordsService.fetchRecordByDoctor(doctor);
	}

	// Fetch records by appointment
	@PostMapping("/by-appointment")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByAppointment(
			@RequestBody Appointment appointment) {
		return medicalRecordsService.fetchRecordByAppointment(appointment);
	}

	// Fetch records by visit date
	@GetMapping("/by-visit-date")
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByVisitDate(
			@RequestParam String visitDate) {
		LocalDateTime dt = LocalDateTime.parse(visitDate);
		return medicalRecordsService.fetchRecordByVisitDate(dt);
	}
}
