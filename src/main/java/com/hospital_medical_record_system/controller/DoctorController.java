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
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.service.DoctorService;

@RestController
@RequestMapping("/department/api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	// Insert doctor
	@PostMapping
	public ResponseEntity<ResponseStructureDto<Doctor>> addDoctorRecord(@RequestBody Doctor doctor) {
		return doctorService.addDoctorRecord(doctor);
	}

	// Get all doctors
	@GetMapping
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	// Get doctor by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id);
	}

	@GetMapping("/specialization/{specialization}")
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorBySpecialization(@PathVariable String specialization) {
		return doctorService.getDoctorBySpecialization(specialization);
	}

	// Fetch doctors by department
	@PostMapping("/department")
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsInDepartment(
			@RequestBody Department department) {
		return doctorService.getDoctorsInDepartment(department);
	}

	// Fetch doctors by patient
	@PostMapping("/patient")
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsByPatient(@RequestBody Patient patient) {
		return doctorService.getDoctorsByPatient(patient);
	}

	// Fetch doctor by appointment
	@PostMapping("/appointment")
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAppointment(@RequestBody Appointment appointment) {
		return doctorService.getDoctorByAppointment(appointment);
	}

	// Fetch doctor by available days
	@GetMapping("/available-days/{days}")
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAvailableDays(
			@PathVariable("days") String availableDays) {
		return doctorService.getDoctorByAvailableDays(availableDays);
	}

	// Update doctor
	@PutMapping
	public ResponseEntity<ResponseStructureDto<Doctor>> updateDoctor(@RequestBody Doctor doctor) {
		return doctorService.updateDoctor(doctor);
	}

	// Delete doctor
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<Long>> deleteDoctor(@PathVariable Long id) {
		return doctorService.deleteDoctor(id);
	}
}
