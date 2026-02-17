package com.hospital_medical_record_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.MedicalRecordsDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Appointment;
import com.hospital_medical_record_system.entity.Doctor;
import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.entity.Patient;
import com.hospital_medical_record_system.exception.MedicalRecordException;
import com.hospital_medical_record_system.repository.AppointmentRepository;

@Service
public class MedicalRecordsService {

	@Autowired
	private MedicalRecordsDao medicalRecordsDao;

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Create a new medical record
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> createRecord(MedicalRecords record) {
		Long pId = record.getPatient().getPatientId();
		Long dId = record.getDoctor().getDoctorId();

		Optional<Appointment> appointmentOpt = appointmentRepository.findCompletedAppointment(pId, dId);

		if (appointmentOpt.isEmpty()) {
			throw new MedicalRecordException("Cannot save record: No completed appointment found.");
		}

		// --- IMPROVEMENTS START HERE ---
		// 1. Set the actual data from the found appointment to the record
		record.setPatient(appointmentOpt.get().getPatient());
		record.setDoctor(appointmentOpt.get().getDoctor());

		// 2. Set the current time as the visit date
		record.setVisitDate(LocalDateTime.now());
		// -------------------------------

		MedicalRecords saved = medicalRecordsDao.createRecord(record);

		ResponseStructureDto<MedicalRecords> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Medical record created successfully");
		response.setData(saved);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Fetch all records
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchAllRecords() {
		List<MedicalRecords> records = medicalRecordsDao.fetchAllRecords();
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical records fetched successfully");
		response.setData(records);
		return new ResponseEntity<ResponseStructureDto<List<MedicalRecords>>>(response, HttpStatus.OK);
	}

	// Fetch record by ID
	public ResponseEntity<ResponseStructureDto<MedicalRecords>> fetchRecordById(Long id) {
		MedicalRecords record = medicalRecordsDao.fetchRecordById(id);
		ResponseStructureDto<MedicalRecords> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical record fetched successfully");
		response.setData(record);
		return new ResponseEntity<ResponseStructureDto<MedicalRecords>>(response, HttpStatus.OK);
	}

	// Fetch records by patient
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByPatient(Patient patient) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByPatient(patient);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical records fetched by patient successfully");
		response.setData(records);
		return new ResponseEntity<ResponseStructureDto<List<MedicalRecords>>>(response, HttpStatus.OK);
	}

	// Fetch records by doctor
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByDoctor(Doctor doctor) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByDoctor(doctor);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical records fetched by doctor successfully");
		response.setData(records);
		return new ResponseEntity<ResponseStructureDto<List<MedicalRecords>>>(response, HttpStatus.OK);
	}

	// Fetch records by appointment
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByAppointment(
			Appointment appointment) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByAppointment(appointment);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical records fetched by appointment successfully");
		response.setData(records);
		return new ResponseEntity<ResponseStructureDto<List<MedicalRecords>>>(response, HttpStatus.OK);
	}

	// Fetch records by visit date
	public ResponseEntity<ResponseStructureDto<List<MedicalRecords>>> fetchRecordByVisitDate(LocalDateTime visitDate) {
		List<MedicalRecords> records = medicalRecordsDao.fetchRecordByVisitDate(visitDate);
		ResponseStructureDto<List<MedicalRecords>> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Medical records fetched by visit date successfully");
		response.setData(records);
		return new ResponseEntity<ResponseStructureDto<List<MedicalRecords>>>(response, HttpStatus.OK);
	}
}
