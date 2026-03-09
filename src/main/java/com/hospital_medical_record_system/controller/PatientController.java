package com.hospital_medical_record_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	// Register new patient
	@PostMapping("/register")
	public ResponseEntity<ResponseStructureDto<Patient>> registerPatient(@RequestBody Patient patient) {
		return patientService.registerPatient(patient);
	}

	// Fetch all patients
	@GetMapping
	public ResponseEntity<ResponseStructureDto<List<Patient>>> fetchAllPatients() {
		return patientService.fetchAllPatients();
	}

	// Fetch patient by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientById(@PathVariable Long id) {
		return patientService.fetchPatientById(id);
	}

	// Fetch patient by phone number
	@GetMapping("/phone/{phone}")
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByPhone(@PathVariable String phone) {
		return patientService.fetchPatientByPhone(phone);
	}

	// Fetch patient(s) with maximum age
	@GetMapping("/max-age")
	public ResponseEntity<ResponseStructureDto<List<Patient>>> fetchPatientWithMaxAge() {
		return patientService.fetchPatientWithMaxAge();
	}

	// PatientController.java mein ye do methods update karein

	// Fetch patient by appointment
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByAppointment(
	        @PathVariable Long appointmentId) { // PathVariable use karein
	    return patientService.fetchPatientByAppointment(appointmentId);
	}

	// Fetch patient by medical record
	@GetMapping("/medical-record/{medicalRecordId}")
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByMedicalRecord(
	        @PathVariable Long medicalRecordId) { // PathVariable use karein
	    return patientService.fetchPatientByMedicalRecord(medicalRecordId);
	}

	// Update patient info
	@PutMapping("/update")
	public ResponseEntity<ResponseStructureDto<Patient>> updatePatientInfo(@RequestBody Patient patient) {
		return patientService.updatePatientInfo(patient);
	}

	// Delete patient info
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructureDto<Long>> deletePatientInfo(@PathVariable Long id) {
		return patientService.deletePatientInfo(id);
	}
}
