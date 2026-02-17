package com.hospital_medical_record_system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital_medical_record_system.entity.Department;
import com.hospital_medical_record_system.exception.IdNotFoundException;
import com.hospital_medical_record_system.repository.DepartmentRepository;

@Repository
public class DepartmentDao {

	@Autowired
	private DepartmentRepository departmentRepository;

	// insert dept record
	public Department createDept(Department department) {
		return departmentRepository.save(department);
	}

	// fetch dept record
	public List<Department> getDeptRecord() {
		return departmentRepository.findAll();
	}

	// fetch dept by id
	public Department getDeptRecordById(Long id) {
		Optional<Department> opt = departmentRepository.findById(id);
		if (!opt.isEmpty()) {
			return opt.get();
		}
		throw new IdNotFoundException("Department Id not Found In Database");
	}

	// update dept
	public Department updateDeptRecord(Department department) {

		if (department.getDepartmentId() == null) {
			return null;
		}

		Optional<Department> opt = departmentRepository.findById(department.getDepartmentId());

		if (!opt.isEmpty()) {
			return departmentRepository.save(department);
		} else {
			throw new IdNotFoundException("Id does not Exist in the Database");
		}
	}

	// delete dept
	public Long deleteDeptRecordById(Long id) {
		Optional<Department> opt = departmentRepository.findById(id);
		if (opt.isPresent()) {
			departmentRepository.deleteById(id);
			return id;
		} else {
			throw new IdNotFoundException("Record with Id does Not Exist in the Database");
		}
	}

	// fetch record by dept name
	public Department getRecordByDeptName(String departmentName) {
		return departmentRepository.findByDepartmentName(departmentName);
	}

}
