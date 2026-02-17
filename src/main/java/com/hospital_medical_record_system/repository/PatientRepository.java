package com.hospital_medical_record_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	// Fetch patient by phone number (Also Business Logic Implementation)
	Patient findByPhone(String phone);

	Patient findByEmail(String email);

	// Fetch patients whose age is greater than all values in database
	@Query("SELECT p FROM Patient p WHERE p.age >= ALL (SELECT age FROM Patient)")
	List<Patient> findPatientWithMaxAge();

	// Fetch patient by appointment
	@Query("SELECT p FROM Patient p JOIN p.appointments a WHERE a = :appointment")
	Patient findPatientByAppointment(Appointment appointment);

	// Fetch patient by medical record
	@Query("SELECT p FROM Patient p JOIN p.medicalRecords m WHERE m = :medicalRecord")
	Patient findPatientByMedicalRecord(MedicalRecords medicalRecord);

}
