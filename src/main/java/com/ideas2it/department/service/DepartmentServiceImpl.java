package com.ideas2it.department.service;

import java.util.List;

import com.ideas2it.department.dao.DepartmentRepositoryImpl;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.department.service.DepartmentService;
import com.ideas2it.exception.DatabaseException;


public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

    
    public void addDepartment(int id, String name) throws DatabaseException {
        Department department = new Department(id, name);
        departmentRepository.addDepartment(department);
    }
	
    public void removeDepartment(int id) throws IllegalArgumentException, DatabaseException {
        Department department = departmentRepository.findDepartmentById(id);
        try {
            departmentRepository.deleteDepartment(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Department not found : " +id);
        }
    }
	
    public List<Department> getAllDepartments() throws DatabaseException {
        return departmentRepository.getAllDepartments();
    }

    
    public Department getDepartmentById(int id) throws DatabaseException {
        return departmentRepository.findDepartmentById(id);
    }
	
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DatabaseException {
        Department department = departmentRepository.findDepartmentById(id);
        try {
            department.setName(name);
            departmentRepository.updateDepartment(department);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Department not found"+id);
        }
    }
	
    public List<Employee> getEmployeesByDepartmentId(int id) throws DatabaseException {
        return departmentRepository.getEmployeesByDepartmentId(id);
    }

}
