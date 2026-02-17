package com.hospital_medical_record_system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.IdNotFoundException;
import com.hospital_medical_record_system.repository.DoctorRepository;

@Repository
public class DoctorDao {

	@Autowired
	private DoctorRepository doctorRepository;

	// insert doctor record
	public Doctor addDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	// fetching all records
	public List<Doctor> getAllDoctors(List<Doctor> doctor) {
		return doctorRepository.findAll();
	}

	// fetching doctor by id
	public Doctor fetchDoctorById(Long id) {
		Optional<Doctor> opt = doctorRepository.findById(id);
		if (!opt.isEmpty()) {
			return opt.get();
		} else {
			throw new IdNotFoundException("Information with Id : " + id + "is not present in the database");
		}
	}

	// Fetch doctor by specialization
	public Doctor getDoctorBySpecialization(String specialization) {
		return doctorRepository.findBySpecialization(specialization);
	}

	// Fetch doctors in a department
	public List<Doctor> getDoctorsInDepartment(Department department) {
		return doctorRepository.fetchDoctorInADept(department);
	}

	// Fetch doctors by patient (via appointment)
	public List<Doctor> getDoctorsByPatient(Patient patient) {
		return doctorRepository.fetchDoctorByPatient(patient);
	}

	// Fetch doctor by appointment
	public Doctor getDoctorByAppointment(Appointment appointment) {
		return doctorRepository.fetchDoctorByAppointment(appointment);
	}

	// Fetch doctor by available days
	public Doctor getDoctorByAvailableDays(String availableDays) {
		return doctorRepository.findByAvailableDays(availableDays);
	}

	// update doctor info
	public Doctor updateDoctorInfo(Doctor doctor) {
		if (doctor.getDoctorId() == null) {
			return null;
		}

		Optional<Doctor> opt = doctorRepository.findById(doctor.getDoctorId());
		if (!opt.isEmpty()) {
			return opt.get();
		} else {
			throw new IdNotFoundException("ID is Not Present in the db");
		}
	}

	// delete doctor record
	public Long deleteDoctor(Long id) {
		Optional<Doctor> opt = doctorRepository.findById(id);
		if (opt.isPresent()) {
			doctorRepository.deleteById(id);
			return id;
		} else {
			throw new IdNotFoundException("Record with Id does Not Exist in the Database");
		}
	}
}
