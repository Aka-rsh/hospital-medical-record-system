package com.hospital_medical_record_system.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.exception.AppointmentException;
import com.hospital_medical_record_system.repository.AppointmentRepository;

@Repository
public class AppointmentDao {

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Book a new appointment
	public Appointment bookAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	// Fetch all appointments
	public List<Appointment> fetchAllAppointments() {
		return appointmentRepository.findAll();
	}

	// Fetch appointment by ID
	public Appointment fetchAppointmentById(Long id) {
		Optional<Appointment> opt = appointmentRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new AppointmentException("Appointment with ID " + id + " not found");
		}
	}

	// Fetch appointments by date
	public List<Appointment> fetchAppointmentByDate(LocalDateTime dateTime) {
		return appointmentRepository.findByAppointmentDateTime(dateTime);
	}

	// Fetch appointments by doctor
	public List<Appointment> fetchAppointmentByDoctor(Doctor doctor) {
		return appointmentRepository.findByDoctor(doctor);
	}

	// Fetch appointments by status
	public List<Appointment> fetchAppointmentByStatus(Appointment.AppointmentStatus status) {
		return appointmentRepository.findByStatus(status);
	}

	// Cancel an appointment
	public Appointment cancelAppointment(Long id) {
		Appointment appointment = fetchAppointmentById(id);
		appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
		return appointmentRepository.save(appointment);
	}

	// Update appointment status
	public Appointment updateAppointmentStatus(Long id, Appointment.AppointmentStatus status) {
		Appointment appointment = fetchAppointmentById(id);
		appointment.setStatus(status);
		return appointmentRepository.save(appointment);
	}
}
