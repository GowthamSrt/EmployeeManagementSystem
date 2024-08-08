package com.ideas2it.employee.service;

import java.util.List;
import java.time.LocalDate;

import com.ideas2it.model.Department;
import com.ideas2it.department.service.DepartmentService;
import com.ideas2it.department.service.DepartmentServiceImpl;
import com.ideas2it.employee.dao.EmployeeRepositoryImpl;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Project;
import com.ideas2it.project.service.ProjectService;
import com.ideas2it.project.service.ProjectServiceImpl;
import com.ideas2it.exception.DatabaseException;




public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();

    public void addEmployee(int id, String name, LocalDate dob, String emailId,
                            String mobile, int departmentId) 
                            throws IllegalArgumentException, DatabaseException {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            Employee employee = new Employee(id, name, dob, department, emailId, mobile); 
            employeeRepository.addEmployee(employee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException ("No such department" + departmentId);
        }     
    }

    public void removeEmployee(int id) throws IllegalArgumentException, 
                                               DatabaseException {
        try {
            Employee employee = employeeRepository.findEmployeeById(id);
            if (employee != null) {
                employeeRepository.deleteEmployee(id);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }

    public List<Employee> getAllEmployees() throws DatabaseException {
        return employeeRepository.getAllEmployees();
    }

    public Employee getEmployeeById(int id) throws DatabaseException {
        return employeeRepository.findEmployeeById(id);
    }        

    public void updateEmployee(int id, String name, LocalDate dob,
                               String emailId, String mobile, int departmentId)
                               throws IllegalArgumentException, DatabaseException {
        try {
            Employee employee = employeeRepository.findEmployeeById(id);
            if (employee != null) {
                employee.setName(name);
                employee.setDob(dob);
                employee.setEmailId(emailId);

                Department department = departmentService.getDepartmentById(departmentId);
                if (department != null) {
                    employee.setDepartment(department);
                } 
                employeeRepository.updateEmployee(employee);
            } 
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }

    public Department getDepartmentById(int id) throws DatabaseException {
        return departmentService.getDepartmentById(id);
    }

    public List<Department>getAllDepartment() throws DatabaseException {
       return departmentService.getAllDepartments();
    }

    public void addProjectToEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, DatabaseException {

        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.addProjectToEmployee(employeeId, projectId);
        
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + projectId + employeeId);
        }
    }

    public void removeProjectFromEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, DatabaseException {
        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.removeProjectFromEmployee(employeeId, projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + projectId + employeeId);
        }
    }

    public List<Project> getAllProjects() throws DatabaseException {
        return projectService.getAllProjects();
    }
}

