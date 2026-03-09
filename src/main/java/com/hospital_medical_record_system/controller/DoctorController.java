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
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.service.DoctorService;

@RestController
@RequestMapping("/doctor")
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
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorBySpecialization(@PathVariable String specialization) {
	    return doctorService.getDoctorBySpecialization(specialization);
	}

	// Fetch doctors by department
	@GetMapping("/department/{deptId}")
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsInDepartment(
	        @PathVariable("deptId") Long deptId) { 
	    
	    Department department = new Department();
	    department.setDepartmentId(deptId);
	    
	    return doctorService.getDoctorsInDepartment(department);
	}

	// Fetch doctors by patient
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsByPatient(@PathVariable Long patientId) {
	    return doctorService.getDoctorsByPatient(patientId);
	}

	// Fetch doctor by appointment
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAppointment(@PathVariable Long appointmentId) {
	    return doctorService.getDoctorByAppointment(appointmentId);
	}

	// Fetch doctor by available days
	@GetMapping("/available-days/{days}")
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorByAvailableDays(
	        @PathVariable("days") List<String> availableDays) {
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
