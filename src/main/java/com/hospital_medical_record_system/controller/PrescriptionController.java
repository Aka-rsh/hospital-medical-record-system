package com.hospital_medical_record_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Prescription;
import com.hospital_medical_record_system.service.PrescriptionService;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;

	@PostMapping("/add/{medicalRecordId}")
	public ResponseStructureDto<Prescription> addPrescription(@RequestBody Prescription prescription,
			@PathVariable Long medicalRecordId) {
		return prescriptionService.addPrescription(prescription, medicalRecordId).getBody();
	}

	@GetMapping("/all")
	public ResponseStructureDto<List<Prescription>> getAllPrescriptions() {
		return prescriptionService.getAllPrescriptions().getBody();
	}

	@GetMapping("/{id}")
	public ResponseStructureDto<Prescription> getPrescriptionById(@PathVariable Long id) {
		return prescriptionService.getPrescriptionById(id).getBody();
	}

	@PutMapping("/update")
	public ResponseStructureDto<Prescription> updatePrescription(@RequestBody Prescription prescription) {
		return prescriptionService.updatePrescription(prescription).getBody();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseStructureDto<Long> deletePrescription(@PathVariable Long id) {
		return prescriptionService.deletePrescription(id).getBody();
	}
}
