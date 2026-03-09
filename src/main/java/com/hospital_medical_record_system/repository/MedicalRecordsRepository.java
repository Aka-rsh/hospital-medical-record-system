package com.hospital_medical_record_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.MedicalRecords;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {

	// Fetch records by patient
	List<MedicalRecords> findByPatient_PatientId(Long patientId);

	// Fetch records by doctor
	List<MedicalRecords> findByDoctor_DoctorId(Long doctorId);
	
	
	@Query("SELECT m FROM MedicalRecords m WHERE m.patient.patientId = (SELECT a.patient.patientId FROM Appointment a WHERE a.appointmentId = :aId)")
	List<MedicalRecords> findRecordsByAppointmentId(@Param("aId") Long appointmentId);

	// Fetch records by visit date
	List<MedicalRecords> findByVisitDate(LocalDateTime visitDate);

}
