package com.hospital_medical_record_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_medical_record_system.dto.ResponseStructureDto;
import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// insert department records
	@PostMapping
	public ResponseEntity<ResponseStructureDto<Department>> createDept(@RequestBody Department department) {
		return departmentService.createDept(department);
	}

	// fetch dept records
	@GetMapping
	public ResponseEntity<ResponseStructureDto<List<Department>>> getDept() {
		return departmentService.getDeptRecord();
	}

	// fetch record by id
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructureDto<Department>> getDeptRecordById(@PathVariable Long id) {
		return departmentService.getDeptById(id);
	}

	// update record
	@PutMapping
	public ResponseEntity<ResponseStructureDto<Department>> updateDeptRecord(@RequestBody Department department) {
		return departmentService.updateDept(department);
	}

	// delete record
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructureDto<String>> deleteDeptRecord(@PathVariable Long id) {
		return departmentService.deleteDept(id);
	}

	// fetch department by name
	@GetMapping("/name/{departmentName}")
	public ResponseEntity<ResponseStructureDto<Department>> getRecordByDeptName(@PathVariable String departmentName) {

		return departmentService.getRecordByDeptName(departmentName);
	}
}
