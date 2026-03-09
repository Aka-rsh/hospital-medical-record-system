package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.MedicalRecordsDao;
import com.hospital_medical_record_system.dao.PrescriptionDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Prescription;
import com.hospital_medical_record_system.exception.MedicalRecordException;

@Service
public class PrescriptionService {

	@Autowired
	private PrescriptionDao prescriptionDao;

	@Autowired
	private MedicalRecordsDao medicalRecordsDao;

	// Add prescription
	public ResponseEntity<ResponseStructureDto<Prescription>> addPrescription(Prescription prescription,
			Long medicalRecordId) {

		// 1. Check if the Medical Record exists
		MedicalRecords record = medicalRecordsDao.fetchRecordById(medicalRecordId);

		// Logic: Ensure this Medical Record doesn't already have a prescription
		if (record.getPrescription() != null) {
			throw new MedicalRecordException("This Medical Record already has an assigned prescription.");
		}
		
		

		// 2. Save the Prescription first to generate its ID
		Prescription savedPrescription = prescriptionDao.addPrescription(prescription);

		// 3. CRUCIAL STEP: Link the prescription to the record and update the record
		// Since MedicalRecords owns the foreign key, we must save the record to update
		// the DB column
		record.setPrescription(savedPrescription); 
		medicalRecordsDao.createRecord(record); // This updates the record in DB with the new prescription_id

		ResponseStructureDto<Prescription> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Prescription added and linked to Medical Record " + medicalRecordId);
		response.setData(savedPrescription);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//  Get all prescriptions (with Else Part)
	public ResponseEntity<ResponseStructureDto<List<Prescription>>> getAllPrescriptions() {
	    List<Prescription> list = prescriptionDao.getAllPrescriptions();
	    ResponseStructureDto<List<Prescription>> response = new ResponseStructureDto<>();

	    if (!list.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("All prescriptions fetched successfully");
	        response.setData(list);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.setStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("No prescriptions found in the database.");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	// Fetch Prescription by ID (Aapne DAO mein exception handle kiya hai, 
	// par Service mein standard response dena achha hota hai)
	public ResponseEntity<ResponseStructureDto<Prescription>> getPrescriptionById(Long id) {
	    Prescription prescription = prescriptionDao.fetchPrescriptionById(id);
	    ResponseStructureDto<Prescription> response = new ResponseStructureDto<>();
	    
	    // DAO exception throw karega agar null hua, isliye yahan direct success logic
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Prescription fetched successfully");
	    response.setData(prescription);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update prescription
	public ResponseEntity<ResponseStructureDto<Prescription>> updatePrescription(Prescription prescription) {
		Prescription updated = prescriptionDao.updatePrescription(prescription);

		ResponseStructureDto<Prescription> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Prescription updated successfully");
		response.setData(updated);

		return new ResponseEntity<ResponseStructureDto<Prescription>>(response, HttpStatus.OK);
	}

	// Delete prescription
	public ResponseEntity<ResponseStructureDto<Long>> deletePrescription(Long id) {
	    Prescription prescription = prescriptionDao.fetchPrescriptionById(id);

	    if (prescription.getMedicalRecords() != null) {
	        MedicalRecords record = prescription.getMedicalRecords();
	        record.setPrescription(null); // Link null kiya
	        medicalRecordsDao.createRecord(record); // Medical Record ko update kiya
	    }

	    Long deletedId = prescriptionDao.deletePrescription(id);

	    ResponseStructureDto<Long> response = new ResponseStructureDto<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Prescription deleted and link removed from Medical Record");
	    response.setData(deletedId);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
