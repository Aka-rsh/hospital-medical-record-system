package com.hospital_medical_record_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	// Fetch patient by phone number (Also Business Logic Implementation)
	Patient findByPhone(String phone);

	Patient findByEmail(String email);

	// Fetch patients whose age is greater than all values in database
	@Query("SELECT p FROM Patient p WHERE p.age >= ALL (SELECT age FROM Patient)")
	List<Patient> findPatientWithMaxAge();

	// PatientRepository.java mein changes karein
	@Query("SELECT p FROM Patient p JOIN p.appointments a WHERE a.appointmentId = :aId")
	Patient findPatientByAppointmentId(@Param("aId") Long appointmentId);

	@Query("SELECT p FROM Patient p JOIN p.medicalRecords m WHERE m.id = :mId")
	Patient findPatientByMedicalRecordId(@Param("mId") Long medicalRecordId);

}
