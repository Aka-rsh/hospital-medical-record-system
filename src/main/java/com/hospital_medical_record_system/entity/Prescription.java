package com.hospital_medical_record_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionId;

	private String medicine;
	private Integer dosage;
	private String instruction;

	@OneToOne(mappedBy = "prescription")
	@JsonIgnore
	private MedicalRecords medicalRecords;

	// Getters and Setters
	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public Integer getDosage() {
		return dosage;
	}

	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
}
