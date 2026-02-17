package com.hospital_medical_record_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	// Optional: find prescription by medicine name
	Prescription findByMedicine(String medicine);
}
