package com.hospital_medical_record_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {

	// Fetch records by patient
	List<MedicalRecords> findByPatient(Patient patient);

	// Fetch records by doctor
	List<MedicalRecords> findByDoctor(Doctor doctor);

	// Fetch records by appointment (via patient’s appointment list)
	List<MedicalRecords> findByPatient_Appointments(Appointment appointment);

	// Fetch records by visit date
	List<MedicalRecords> findByVisitDate(LocalDateTime visitDate);

}
