package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.PatientDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
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

        if (!patients.isEmpty()) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("All patients fetched successfully");
            response.setData(patients);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // ELSE PART: Jab list khali ho
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No patients found in the database.");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
	    
	    if (patient != null) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Patient found");
	        response.setData(patient);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No patient registered with phone: " + phone);
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}



    // Fetch Patients with Maximum Age
    public ResponseEntity<ResponseStructureDto<List<Patient>>> fetchPatientWithMaxAge() {
        List<Patient> patients = patientDao.fetchPatientWithMaxAge();
        ResponseStructureDto<List<Patient>> response = new ResponseStructureDto<>();

        if (patients != null && !patients.isEmpty()) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Patients with maximum age fetched successfully");
            response.setData(patients);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // ELSE PART: Agar koi record na mile
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No patient records found to calculate max age.");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Fetch Patient by Appointment
 // PatientService.java

 // Fetch by Appointment ID
 public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByAppointment(Long appointmentId) {
     Patient patient = patientRepository.findPatientByAppointmentId(appointmentId);
     ResponseStructureDto<Patient> response = new ResponseStructureDto<>();

     if (patient != null) {
         response.setStatusCode(HttpStatus.OK.value());
         response.setMessage("Patient found for Appointment ID: " + appointmentId);
         response.setData(patient);
         return new ResponseEntity<>(response, HttpStatus.OK);
     } else {
         // ELSE PART: Agar matching ID na mile
         response.setStatusCode(HttpStatus.NOT_FOUND.value());
         response.setMessage("No patient linked with Appointment ID: " + appointmentId);
         response.setData(null);
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
     }
 }

    //  Fetch Patient by Medical Record
    public ResponseEntity<ResponseStructureDto<Patient>> fetchPatientByMedicalRecord(Long medicalRecordId) {
        Patient patient = patientDao.fetchPatientByMedicalRecord(medicalRecordId);
        ResponseStructureDto<Patient> response = new ResponseStructureDto<>();

        if (patient != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Patient fetched successfully by medical record");
            response.setData(patient);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // ELSE PART: Agar record linked nahi hai
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No patient found linked to this medical record.");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

	// Update patient info
	public ResponseEntity<ResponseStructureDto<Patient>> updatePatientInfo(Patient patient) {
		
		// 1. Check if the email being updated belongs to someone else
	    Patient existingEmail = patientRepository.findByEmail(patient.getEmail());
	    if (existingEmail != null && !existingEmail.getPatientId().equals(patient.getPatientId())) {
	        throw new DuplicateEntryException("Cannot update: Email already in use by another patient.");
	    }

	    // 2. Check if the phone being updated belongs to someone else
	    Patient existingPhone = patientRepository.findByPhone(patient.getPhone());
	    if (existingPhone != null && !existingPhone.getPatientId().equals(patient.getPatientId())) {
	        throw new DuplicateEntryException("Cannot update: Phone number already in use by another patient.");
	    }
		
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
