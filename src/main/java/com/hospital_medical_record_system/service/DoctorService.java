package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.DoctorDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.entity.Doctor;

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
		List<Doctor> doctors = doctorDao.getAllDoctors();

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
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorBySpecialization(String specialization) {
	    List<Doctor> doctors = doctorDao.getDoctorBySpecialization(specialization);
	    ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();

	    if (!doctors.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctors with specialization " + specialization + " fetched successfully");
	        response.setData(doctors);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No doctors found with specialization: " + specialization);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch by Department
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsInDepartment(Department department) {
	    List<Doctor> doctors = doctorDao.getDoctorsInDepartment(department);
	    ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();

	    if (!doctors.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctors in department fetched successfully");
	        response.setData(doctors);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No doctors found in the specified department");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch doctors by patient
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorsByPatient(Long patientId) {
	    List<Doctor> doctors = doctorDao.getDoctorsByPatient(patientId);
	    ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();

	    if (!doctors.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctors fetched successfully for Patient ID: " + patientId);
	        response.setData(doctors);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No doctors found for Patient ID: " + patientId + " (Check if appointment exists)");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch doctor by appointment
	public ResponseEntity<ResponseStructureDto<Doctor>> getDoctorByAppointment(Long appointmentId) {
	    Doctor doctor = doctorDao.getDoctorByAppointment(appointmentId);
	    ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();

	    if (doctor != null) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctor fetched successfully for Appointment ID: " + appointmentId);
	        response.setData(doctor);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No doctor found for Appointment ID: " + appointmentId);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch doctor by available days
	public ResponseEntity<ResponseStructureDto<List<Doctor>>> getDoctorByAvailableDays(List<String> availableDays) {
	    List<Doctor> doctors = doctorDao.getDoctorByAvailableDays(availableDays);
	    ResponseStructureDto<List<Doctor>> response = new ResponseStructureDto<>();

	    if (!doctors.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctors fetched successfully");
	        response.setData(doctors);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No doctors found for days: " + availableDays);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Update doctor
	public ResponseEntity<ResponseStructureDto<Doctor>> updateDoctor(Doctor doctor) {
	    Doctor updatedDoctor = doctorDao.updateDoctorInfo(doctor);
	    ResponseStructureDto<Doctor> response = new ResponseStructureDto<>();

	    if (updatedDoctor != null) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctor record updated successfully");
	        response.setData(updatedDoctor);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        response.setMessage("Update failed: Invalid Doctor ID");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
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
