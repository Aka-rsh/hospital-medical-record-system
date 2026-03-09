package com.hospital_medical_record_system.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.MedicalRecords;
import com.hospital_medical_record_system.exception.MedicalRecordException;
import com.hospital_medical_record_system.repository.MedicalRecordsRepository;

@Repository
public class MedicalRecordsDao {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecords createRecord(MedicalRecords record) {
        return medicalRecordsRepository.save(record);
    }

    public List<MedicalRecords> fetchAllRecords() {
        return medicalRecordsRepository.findAll();
    }

    public MedicalRecords fetchRecordById(Long id) {
        return medicalRecordsRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException("Medical record with ID " + id + " not found"));
    }

    public List<MedicalRecords> fetchRecordByPatient(Long patientId) {
        return medicalRecordsRepository.findByPatient_PatientId(patientId);
    }

    public List<MedicalRecords> fetchRecordByDoctor(Long doctorId) {
        return medicalRecordsRepository.findByDoctor_DoctorId(doctorId);
    }

    public List<MedicalRecords> fetchRecordByAppointment(Long appointmentId) {
        return medicalRecordsRepository.findRecordsByAppointmentId(appointmentId);
    }

    public List<MedicalRecords> fetchRecordByVisitDate(LocalDateTime visitDate) {
        return medicalRecordsRepository.findByVisitDate(visitDate);
    }
}
