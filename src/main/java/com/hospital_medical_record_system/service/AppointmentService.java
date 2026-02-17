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

		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		appointment.setDoctor(doctor);
		appointment.setPatient(patient);

		// Business Logic
		// 1. Define the start and end of the day for the requested appointment
		LocalDateTime startOfDay = appointment.getAppointmentDateTime().toLocalDate().atStartOfDay();
		LocalDateTime endOfDay = appointment.getAppointmentDateTime().toLocalDate().atTime(23, 59, 59);

		// 2. Check if the patient already has an appointment on this day
		Optional<Appointment> existing = appointmentRepository
				.findAppointmentByPatientAndDate(appointment.getPatient().getPatientId(), startOfDay, endOfDay);

		if (existing.isPresent()) {
			throw new DuplicateEntryException("Patient already has an appointment scheduled for this date: "
					+ appointment.getAppointmentDateTime().toLocalDate());
		}

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
		List<Appointment> appointments = appointmentDao.fetchAppointmentByDate(dateTime);
		ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointments fetched by date successfully");
		response.setData(appointments);
		return new ResponseEntity<ResponseStructureDto<List<Appointment>>>(response, HttpStatus.OK);
	}

	// Fetch appointments by doctor
//    public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDoctor(Doctor doctor) {
//        List<Appointment> appointments = appointmentDao.fetchAppointmentByDoctor(doctor);
//        ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();
//        response.setStatusCode(HttpStatus.OK.value());
//        response.setMessage("Appointments fetched by doctor successfully");
//        response.setData(appointments);
//        return new ResponseEntity<ResponseStructureDto<List<Appointment>>>(response, HttpStatus.OK);
//    }

	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByDoctor(Doctor doctor) {

		if (doctor == null || doctor.getDoctorId() == null) {
			throw new RuntimeException("Doctor ID is required");
		}

		List<Appointment> appointments = appointmentDao.fetchAppointmentByDoctor(doctor);

		ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointments fetched successfully");
		response.setData(appointments);
		return new ResponseEntity<ResponseStructureDto<List<Appointment>>>(response, HttpStatus.OK);
	}

	// Fetch appointments by status
	public ResponseEntity<ResponseStructureDto<List<Appointment>>> fetchAppointmentByStatus(
			Appointment.AppointmentStatus status) {
		List<Appointment> appointments = appointmentDao.fetchAppointmentByStatus(status);
		ResponseStructureDto<List<Appointment>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointments fetched by status successfully");
		response.setData(appointments);
		return new ResponseEntity<ResponseStructureDto<List<Appointment>>>(response, HttpStatus.OK);
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
