package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.PatientDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.DuplicateEntryException;
import com.hospital_medical_record_system.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientDao patientDao;

	@Autowired
	private PatientRepository patientRepository;

	// Register patient
	public ResponseEntity<ResponseStructureDto<Patient>> registerPatient(Patient patient) {
		// Check Phone
		if (patientRepository.findByPhone(patient.getPhone()) != null) {
			throw new DuplicateEntryException("The phone number " + patient.getPhone() + " is already in use.");
		}

		// Check Email
		if (patientRepository.findByEmail(patient.getEmail()) != null) {
			throw new DuplicateEntryException("The email " + patient.getEmail() + " is already in use.");
		}
		Patient savedPatient = patientDao.registerPatient(patient);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Patient registered successfully");
		response.setData(savedPatient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.CREATED);
	}

	// Fetch all patients
	public ResponseEntity<ResponseStructureDto<List<Patient>>> fetchAllPatients() {
		List<Patient> patients = patientDao.fetchAllPatients();

		ResponseStructureDto<List<Patient>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All patients fetched successfully");
		response.setData(patients);

		return new ResponseEntity<ResponseStructureDto<List<Patient>>>(response, HttpStatus.OK);
	}

	// Fetch patient by ID
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientById(Long id) {
		Patient patient = patientDao.fetchPatientById(id);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient fetched successfully by ID");
		response.setData(patient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.OK);
	}

	// Fetch patient by phone number
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByPhone(String phone) {
		Patient patient = patientDao.fetchPatientByPhone(phone);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient fetched successfully by phone number");
		response.setData(patient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.OK);
	}

	// Fetch patients with maximum age
	public ResponseEntity<ResponseStructureDto<List<Patient>>> fetchPatientWithMaxAge() {
		List<Patient> patients = patientDao.fetchPatientWithMaxAge();

		ResponseStructureDto<List<Patient>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patients with maximum age fetched successfully");
		response.setData(patients);

		return new ResponseEntity<ResponseStructureDto<List<Patient>>>(response, HttpStatus.OK);
	}

	// Fetch patient by appointment
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByAppointment(Appointment appointment) {
		Patient patient = patientDao.fetchPatientByAppointment(appointment);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient fetched successfully by appointment");
		response.setData(patient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.OK);
	}

	// Fetch patient by medical record
	public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByMedicalRecord(MedicalRecords medicalRecord) {
		Patient patient = patientDao.fetchPatientByMedicalRecord(medicalRecord);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient fetched successfully by medical record");
		response.setData(patient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.OK);
	}

	// Update patient info
	public ResponseEntity<ResponseStructureDto<Patient>> updatePatientInfo(Patient patient) {
		Patient updatedPatient = patientDao.updatePatientInfo(patient);

		ResponseStructureDto<Patient> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient information updated successfully");
		response.setData(updatedPatient);

		return new ResponseEntity<ResponseStructureDto<Patient>>(response, HttpStatus.OK);
	}

	// Delete patient info
	public ResponseEntity<ResponseStructureDto<Long>> deletePatientInfo(Long id) {
		Long deletedId = patientDao.deletePatientInfo(id);

		ResponseStructureDto<Long> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Patient record deleted successfully");
		response.setData(deletedId);

		return new ResponseEntity<ResponseStructureDto<Long>>(response, HttpStatus.OK);
	}
}
