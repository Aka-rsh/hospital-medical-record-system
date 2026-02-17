package com.hospital_medical_record_system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.IdNotFoundException;
import com.hospital_medical_record_system.repository.PatientRepository;

@Repository
public class PatientDao {

	@Autowired
	private PatientRepository patientRepository;

	// Register patient
	public Patient registerPatient(Patient patient) {
		return patientRepository.save(patient);
	}

	// Fetch all patients
	public List<Patient> fetchAllPatients() {
		return patientRepository.findAll();
	}

	// Fetch patient by ID
	public Patient fetchPatientById(Long id) {
		Optional<Patient> opt = patientRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IdNotFoundException("Patient with Id: " + id + " not found");
		}
	}

	// Fetch patient by phone number
	public Patient fetchPatientByPhone(String phone) {
		return patientRepository.findByPhone(phone);
	}

	// Fetch patient where age is maximum
	public List<Patient> fetchPatientWithMaxAge() {
		return patientRepository.findPatientWithMaxAge();
	}

	// Fetch patient by appointment
	public Patient fetchPatientByAppointment(Appointment appointment) {
		return patientRepository.findPatientByAppointment(appointment);
	}

	// Fetch patient by medical record
	public Patient fetchPatientByMedicalRecord(MedicalRecords medicalRecord) {
		return patientRepository.findPatientByMedicalRecord(medicalRecord);
	}

	// Update patient info
	public Patient updatePatientInfo(Patient patient) {
		if (patient.getPatientId() == null) {
			return null;
		}
		Optional<Patient> opt = patientRepository.findById(patient.getPatientId());
		if (opt.isPresent()) {
			return patientRepository.save(patient);
		} else {
			throw new IdNotFoundException("Patient ID not present in DB");
		}
	}

	// Delete patient info
	public Long deletePatientInfo(Long id) {
		Optional<Patient> opt = patientRepository.findById(id);
		if (opt.isPresent()) {
			patientRepository.deleteById(id);
			return id;
		} else {
			throw new IdNotFoundException("Patient with ID " + id + " does not exist");
		}
	}
}
