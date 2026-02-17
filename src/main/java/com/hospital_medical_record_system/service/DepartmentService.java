package com.hospital_medical_record_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital_medical_record_system.dao.DepartmentDao;
import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.exception.DuplicateDepartmentNameException;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	// insert dept record
	public ResponseEntity<ResponseStructureDto<Department>> createDept(Department department) {

		// Business Validation
		Department existing = departmentDao.getRecordByDeptName(department.getDepartmentName());
		if (existing != null) {
			throw new DuplicateDepartmentNameException(
					"Department with name '" + department.getDepartmentName() + "' already exists.");
		}

		Department dept = departmentDao.createDept(department);
		ResponseStructureDto<Department> response = new ResponseStructureDto<Department>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Department is created");
		response.setData(dept);
		return new ResponseEntity<ResponseStructureDto<Department>>(response, HttpStatus.CREATED);
	}

	// fetch dept records
	public ResponseEntity<ResponseStructureDto<List<Department>>> getDeptRecord() {
		List<Department> dept = departmentDao.getDeptRecord();
		ResponseStructureDto<List<Department>> response = new ResponseStructureDto<List<Department>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All department Record fetch successfully");
		response.setData(dept);
		return new ResponseEntity<ResponseStructureDto<List<Department>>>(response, HttpStatus.OK);
	}

	// fetch dept by id
	public ResponseEntity<ResponseStructureDto<Department>> getDeptById(Long id) {
		Department dept = departmentDao.getDeptRecordById(id);
		ResponseStructureDto<Department> response = new ResponseStructureDto<Department>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("department Record with Id : " + id + " fetch successfully");
		response.setData(dept);
		return new ResponseEntity<ResponseStructureDto<Department>>(response, HttpStatus.OK);
	}

	// update Department
	public ResponseEntity<ResponseStructureDto<Department>> updateDept(Department department) {

		// Business Logic: If updating name, ensure the NEW name isn't taken by another
		// ID
		Department existing = departmentDao.getRecordByDeptName(department.getDepartmentName());

		if (existing != null && !existing.getDepartmentId().equals(department.getDepartmentId())) {
			throw new DuplicateDepartmentNameException(
					"Cannot update: Another department already uses the name '" + department.getDepartmentName() + "'");
		}

		ResponseStructureDto<Department> response = new ResponseStructureDto<>();

		// safety check
		if (department.getDepartmentId() == null) {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setMessage("Department ID is required for update");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Department dept = departmentDao.updateDeptRecord(department);

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Department Record Updated Successfully");
		response.setData(dept);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// delete dept record
	public ResponseEntity<ResponseStructureDto<String>> deleteDept(Long id) {
		Long deleteDept = departmentDao.deleteDeptRecordById(id);
		ResponseStructureDto<String> response = new ResponseStructureDto<String>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Department Record Deleted Successfully");
		response.setData("Deleted Id : " + deleteDept);
		return new ResponseEntity<ResponseStructureDto<String>>(response, HttpStatus.OK);
	}

	// fetch record by dept name
	public ResponseEntity<ResponseStructureDto<Department>> getRecordByDeptName(String departmentName) {

		Department dept = departmentDao.getRecordByDeptName(departmentName);

		if (dept == null) {
			ResponseStructureDto<Department> response = new ResponseStructureDto<>();
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("Department not found");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		ResponseStructureDto<Department> response = new ResponseStructureDto<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Department fetched successfully");
		response.setData(dept);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
