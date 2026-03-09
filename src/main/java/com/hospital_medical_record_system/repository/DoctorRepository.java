package com.hospital_medical_record_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	// Fetch doctor by specialization
	List<Doctor> findBySpecialization(String specialization);

	// Fetch doctor by available days
	// Match the name to your Entity field for clarity
	@Query(value = "SELECT * FROM doctor d WHERE EXISTS (SELECT 1 FROM unnest(d.available_days) as day WHERE day IN :availableDays)", nativeQuery = true)
	List<Doctor> findByAvailableDays(@Param("availableDays") List<String> availableDays);

	// Fetch doctors in a department
	@Query("SELECT d FROM Doctor d WHERE d.department = :department")
	List<Doctor> fetchDoctorInADept(@Param("department") Department department);

	// Fetch doctors by patient (via Appointment)
	@Query("""
		    SELECT DISTINCT d
		    FROM Doctor d
		    JOIN d.appointments a
		    WHERE a.patient.patientId = :pId
		""")
		List<Doctor> fetchDoctorByPatientId(@Param("pId") Long patientId);

	// Fetch doctor by appointment
	@Query("""
		    SELECT d 
		    FROM Doctor d 
		    JOIN d.appointments a 
		    WHERE a.appointmentId = :aId
		""")
		Doctor fetchDoctorByAppointment(@Param("aId") Long appointmentId);
}
