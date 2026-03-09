package com.hospital_medical_record_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.AppointmentDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.DuplicateEntryException;
import com.hospital_medical_record_system.repository.AppointmentRepository;
import com.hospital_medical_record_system.repository.DoctorRepository;
import com.hospital_medical_record_system.repository.PatientRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Book a new appointment
   	public ResponseEntity<ResponseStructureDto<Appointment>> bookAppointment(Appointment appointment) {

	    if (appointment.getDoctor() == null || appointment.getPatient() == null) {
	        throw new RuntimeException("Doctor and Patient are required");
	    }

	    Long doctorId = appointment.getDoctor().getDoctorId();
	    Long patientId = appointment.getPatient().getPatientId();

	    Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
	    Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

	    appointment.setDoctor(doctor);
	    appointment.setPatient(patient);

	    // --- BUSINESS LOGIC START ---

	    // 1. Logic for Patient (Jo pehle se tha)
	    LocalDateTime startOfDay = appointment.getAppointmentDateTime().toLocalDate().atStartOfDay();
	    LocalDateTime endOfDay = appointment.getAppointmentDateTime().toLocalDate().atTime(23, 59, 59);

	    Optional<Appointment> existingPatientAppt = appointmentRepository
	            .findActiveAppointmentByPatientAndDate(patientId, startOfDay, endOfDay);

	    if (existingPatientAppt.isPresent()) {
	        throw new DuplicateEntryException("Patient already has an appointment on this date.");
	    }

	    // 2. NEW LOGIC: Doctor Overlap Check (Jo aapne manga hai)
	    // Check if the same doctor has another appointment at the EXACT same time
	    Optional<Appointment> doctorConflict = appointmentRepository
	            .findByDoctorAndAppointmentDateTime(doctor, appointment.getAppointmentDateTime());

	    if (doctorConflict.isPresent()) {
	        throw new DuplicateEntryException("Doctor is already busy at this specific time slot!");
	    }

	    // --- BUSINESS LOGIC END ---

	    Appointment saved = appointmentDao.bookAppointment(appointment);

	    ResponseStructureDto<Appointment> response = new ResponseStructureDto<>();
	    response.setStatusCode(HttpStatus.CREATED.value());
	    response.setMessage("Appointment booked successfully");
	    response.setData(saved);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Fetch all appointments
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAllAppointments() {
		List<Appointment> appointments = appointmentDao.fetchAllAppointments();
		ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointments fetched successfully");
		response.setData(appointments);
		return new ResponseEntity<ResponseStructureDto<List<Appointment>>>(response, HttpStatus.OK);
	}

	// Fetch appointment by ID
	public ResponseEntity<ResponseStructureDto<Appointment>> fetchAppointmentById(Long id) {
		Appointment appointment = appointmentDao.fetchAppointmentById(id);
		ResponseStructureDto<Appointment> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointment fetched successfully");
		response.setData(appointment);
		return new ResponseEntity<ResponseStructureDto<Appointment>>(response, HttpStatus.OK);
	}

	// Fetch appointments by date
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDate(LocalDateTime dateTime) {
	    LocalDateTime startOfDay = dateTime.toLocalDate().atStartOfDay();
	    LocalDateTime endOfDay = dateTime.toLocalDate().atTime(23, 59, 59);

	    // 2. Naya repository method call karein
	    List<Appointment> appointments = appointmentRepository.findAllByDateRange(startOfDay, endOfDay);
	    
	    ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();

	    if (appointments != null && !appointments.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Appointments found for " + dateTime.toLocalDate());
	        response.setData(appointments);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No appointments found on this date.");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}
    
    //fetchappointmentby Doctor
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDoctor(Doctor doctor) {
	    if (doctor == null || doctor.getDoctorId() == null) {
	        throw new RuntimeException("Doctor ID is required");
	    }

	    List<Appointment> appointments = appointmentDao.fetchAppointmentByDoctor(doctor);
	    ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();

	    if (appointments.isEmpty()) {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No appointments found for this doctor");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    } else {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Appointments fetched successfully");
	        response.setData(appointments);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}

	// Fetch appointments by status
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByStatus(
	        Appointment.AppointmentStatus status) {
	    
	    List<Appointment> appointments = appointmentDao.fetchAppointmentByStatus(status);
	    ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();

	    if (!appointments.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Appointments fetched by status successfully");
	        response.setData(appointments);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No appointments found with status: " + status);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Cancel an appointment
	public ResponseEntity<ResponseStructureDto<Appointment>> cancelAppointment(Long id) {
		Appointment canceled = appointmentDao.cancelAppointment(id);
		ResponseStructureDto<Appointment> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointment cancelled successfully");
		response.setData(canceled);
		return new ResponseEntity<ResponseStructureDto<Appointment>>(response, HttpStatus.OK);
	}

	// Update appointment status
	public ResponseEntity<ResponseStructureDto<Appointment>> updateAppointmentStatus(Long id,
			Appointment.AppointmentStatus status) {
		Appointment updated = appointmentDao.updateAppointmentStatus(id, status);
		ResponseStructureDto<Appointment> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointment status updated successfully");
		response.setData(updated);
		return new ResponseEntity<ResponseStructureDto<Appointment>>(response, HttpStatus.OK);
	}
}
