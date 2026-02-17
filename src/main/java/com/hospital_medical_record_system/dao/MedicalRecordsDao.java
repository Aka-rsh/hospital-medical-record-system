package com.hospital_medical_record_system.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.MedicalRecordException;
import com.hospital_medical_record_system.repository.MedicalRecordsRepository;

@Repository
public class MedicalRecordsDao {

	@Autowired
	private MedicalRecordsRepository medicalRecordsRepository;

	// Create a new medical record
	public MedicalRecords createRecord(MedicalRecords record) {
		return medicalRecordsRepository.save(record);
	}

	// Fetch all records
	public List<MedicalRecords> fetchAllRecords() {
		return medicalRecordsRepository.findAll();
	}

	// Fetch record by ID
	public MedicalRecords fetchRecordById(Long id) {
		Optional<MedicalRecords> opt = medicalRecordsRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new MedicalRecordException("Medical record with ID " + id + " not found");
		}
	}

	// Fetch records by patient
	public List<MedicalRecords> fetchRecordByPatient(Patient patient) {
		return medicalRecordsRepository.findByPatient(patient);
	}

	// Fetch records by doctor
	public List<MedicalRecords> fetchRecordByDoctor(Doctor doctor) {
		return medicalRecordsRepository.findByDoctor(doctor);
	}

	// Fetch records by appointment
	public List<MedicalRecords> fetchRecordByAppointment(Appointment appointment) {
		return medicalRecordsRepository.findByPatient_Appointments(appointment);
	}

	// Fetch records by visit date
	public List<MedicalRecords> fetchRecordByVisitDate(LocalDateTime visitDate) {
		return medicalRecordsRepository.findByVisitDate(visitDate);
	}
}
