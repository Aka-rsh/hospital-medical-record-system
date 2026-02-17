package com.hospital_medical_record_system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	// Book a new appointment
	@PostMapping("/book")
	public ResponseEntity<ResponseStructureDto<Appointment>> bookAppointment(@RequestBody Appointment appointment) {
		return appointmentService.bookAppointment(appointment);
	}

	// Fetch all appointments
	@GetMapping
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAllAppointments() {
		return appointmentService.fetchAllAppointments();
	}

	// Fetch appointment by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<Appointment>> fetchAppointmentById(@PathVariable Long id) {
		return appointmentService.fetchAppointmentById(id);
	}

	// Fetch appointments by date
	@GetMapping("/by-date")
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDate(
			@RequestParam String dateTime) {
		LocalDateTime dt = LocalDateTime.parse(dateTime);
		return appointmentService.fetchAppointmentByDate(dt);
	}

	// Fetch appointments by doctor
	@GetMapping("/by-doctor")
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDoctor(
			@RequestBody Doctor doctor) {
		return appointmentService.fetchAppointmentByDoctor(doctor);
	}

	// Fetch appointments by status
	@GetMapping("/by-status")
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByStatus(
			@RequestParam Appointment.AppointmentStatus status) {
		return appointmentService.fetchAppointmentByStatus(status);
	}

	// Cancel appointment
	@PutMapping("/cancel/{id}")
	public ResponseEntity<ResponseStructureDto<Appointment>> cancelAppointment(@PathVariable Long id) {
		return appointmentService.cancelAppointment(id);
	}

	// Update appointment status
	@PutMapping("/update-status/{id}/{status}")
	public ResponseEntity<ResponseStructureDto<Appointment>> updateAppointmentStatus(@PathVariable Long id,
			@PathVariable Appointment.AppointmentStatus status) {
		return appointmentService.updateAppointmentStatus(id, status);
	}
}
