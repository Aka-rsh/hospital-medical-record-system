package com.hospital_medical_record_system.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Appointment.AppointmentStatus;
import com.hospital_medical_record_system.entity.Doctor;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByAppointmentDateTime(LocalDateTime dateTime);

	List<Appointment> findByDoctor(Doctor doctor);

	List<Appointment> findByStatus(AppointmentStatus status);

	List<Appointment> findByDoctorAndStatus(Doctor doctor, String status);

	// One Patient cannot have two Appointments
	@Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :pId "
			+ "AND a.appointmentDateTime >= :startOfDay AND a.appointmentDateTime <= :endOfDay") // Changed to
																									// appointmentDateTime
	Optional<Appointment> findAppointmentByPatientAndDate(@Param("pId") Long patientId,
			@Param("startOfDay") LocalDateTime start, @Param("endOfDay") LocalDateTime end);

	// Medical record must be save only after appointment completion
	@Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :pId "
			+ "AND a.doctor.doctorId = :dId AND a.status = com.hospital_medical_record_system.entity.Appointment.AppointmentStatus.COMPLETED")
	Optional<Appointment> findCompletedAppointment(@Param("pId") Long patientId, @Param("dId") Long doctorId);

}
