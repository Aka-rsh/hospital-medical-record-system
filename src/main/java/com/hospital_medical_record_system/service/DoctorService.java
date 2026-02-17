package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.DoctorDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.Patient;

@Service
public class DoctorService {

	@Autowired
	private DoctorDao doctorDao;

	// Insert doctor record
	public ResponseEntity<ResponseStructureDto<Doctor>> addDoctorRecord(Doctor doctor) {
		Doctor savedDoctor = doctorDao.addDoctor(doctor);

		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Doctor record inserted successfully");
		response.setData(savedDoctor);

		return new ResponseEntity<ResponseStructureDto<Doctor>>(response, HttpStatus.CREATED);
	}

	// Fetch all doctors
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getAllDoctors() {
		List<Doctor> doctors = doctorDao.getAllDoctors(null);

		ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor records fetched successfully");
		response.setData(doctors);

		return new ResponseEntity<ResponseStructureDto<List<Doctor>>>(response, HttpStatus.OK);
	}

	// Fetch doctor by ID
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorById(Long id) {
		Doctor doctor = doctorDao.fetchDoctorById(id);

		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor record fetched successfully");
		response.setData(doctor);

		return new ResponseEntity<ResponseStructureDto<Doctor>>(response, HttpStatus.OK);
	}

	// Fetch doctor by specialization
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorBySpecialization(String specialization) {
		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor fetched by specialization");
		response.setData(doctorDao.getDoctorBySpecialization(specialization));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Fetch doctors by department
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsInDepartment(Department department) {
		ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctors fetched by department");
		response.setData(doctorDao.getDoctorsInDepartment(department));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Fetch doctors by patient
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsByPatient(Patient patient) {
		ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctors fetched by patient");
		response.setData(doctorDao.getDoctorsByPatient(patient));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Fetch doctor by appointment
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAppointment(Appointment appointment) {
		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor fetched by appointment");
		response.setData(doctorDao.getDoctorByAppointment(appointment));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Fetch doctor by available days
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAvailableDays(String availableDays) {
		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor fetched by available days");
		response.setData(doctorDao.getDoctorByAvailableDays(availableDays));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update doctor
	public ResponseEntity<ResponseStructureDto<Doctor>> updateDoctor(Doctor doctor) {
		Doctor updatedDoctor = doctorDao.updateDoctorInfo(doctor);

		ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor record updated successfully");
		response.setData(updatedDoctor);

		return new ResponseEntity<ResponseStructureDto<Doctor>>(response, HttpStatus.OK);
	}

	// Delete doctor
	public ResponseEntity<ResponseStructureDto<Long>> deleteDoctor(Long id) {
		Long deletedId = doctorDao.deleteDoctor(id);

		ResponseStructureDto<Long> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor record deleted successfully");
		response.setData(deletedId);

		return new ResponseEntity<ResponseStructureDto<Long>>(response, HttpStatus.OK);
	}
}
