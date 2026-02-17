package com.hospital_medical_record_system.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class MedicalRecords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recordId;

	private String diagnostic;
	private String treatment;

	@Column(name = "visit_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime visitDate;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToOne
	@JoinColumn(name = "prescription_id")
	private Prescription prescription;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	// Getters and Setters
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public LocalDateTime getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDateTime visitDate) {
		this.visitDate = visitDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}
}
