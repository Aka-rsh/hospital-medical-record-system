package com.hospital_medical_record_system.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;

	private String doctorName;
	private String specialization;

	private String availableDays;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<MedicalRecords> medicalRecords;

	// Getters and Setters
	public Long getDoctorId() {
		return doctorId;
	}

	public List<MedicalRecords> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
}
