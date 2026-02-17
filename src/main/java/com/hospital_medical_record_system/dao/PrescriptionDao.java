package com.hospital_medical_record_system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Prescription;
import com.hospital_medical_record_system.exception.PrescriptionException;
import com.hospital_medical_record_system.repository.PrescriptionRepository;

@Repository
public class PrescriptionDao {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	// Add new prescription
	public Prescription addPrescription(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}

	// Fetch all prescriptions
	public List<Prescription> getAllPrescriptions() {
		return prescriptionRepository.findAll();
	}

	// Fetch prescription by ID
	public Prescription fetchPrescriptionById(Long id) {
		Optional<Prescription> opt = prescriptionRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new PrescriptionException("Prescription with ID " + id + " not found");
		}
	}

	// Update prescription (same logic as DoctorDao)
	public Prescription updatePrescription(Prescription prescription) {
		if (prescription.getPrescriptionId() == null) {
			throw new PrescriptionException("Prescription ID is missing");
		}
		Optional<Prescription> opt = prescriptionRepository.findById(prescription.getPrescriptionId());
		if (opt.isPresent()) {
			return prescriptionRepository.save(prescription);
		} else {
			throw new PrescriptionException("Prescription with ID " + prescription.getPrescriptionId() + " not found");
		}
	}

	// Delete prescription
	public Long deletePrescription(Long id) {
		Optional<Prescription> opt = prescriptionRepository.findById(id);
		if (opt.isPresent()) {
			prescriptionRepository.deleteById(id);
			return id;
		} else {
			throw new PrescriptionException("Prescription with ID " + id + " not found");
		}
	}
}
