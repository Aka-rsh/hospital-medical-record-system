package com.hospital_medical_record_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.Patient;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	// Fetch doctor by specialization
	Doctor findBySpecialization(String specialization);

	// Fetch doctor by available days
	Doctor findByAvailableDays(String availableDays);

	// Fetch doctors in a department
	@Query("SELECT d FROM Doctor d WHERE d.department = :department")
	List<Doctor> fetchDoctorInADept(@Param("department") Department department);

	// Fetch doctors by patient (via Appointment)
	@Query("""
				SELECT DISTINCT d
				FROM Doctor d
				JOIN d.appointments a
				WHERE a.patient = :patient
			""")
	List<Doctor> fetchDoctorByPatient(@Param("patient") Patient patient);

	// Fetch doctor by appointment
	@Query("""
				SELECT d
				FROM Doctor d
				JOIN d.appointments a
				WHERE a = :appointment
			""")
	Doctor fetchDoctorByAppointment(@Param("appointment") Appointment appointment);
}
