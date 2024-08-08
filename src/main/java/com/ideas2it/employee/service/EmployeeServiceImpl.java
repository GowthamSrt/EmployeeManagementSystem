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
import com.ideas2it.exception.EmployeeException;




public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();

    public void addEmployee(int id, String name, LocalDate dob, String emailId,
                            String mobile, int departmentId) 
                            throws IllegalArgumentException, EmployeeException {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            Employee employee = new Employee(id, name, dob, department, emailId, mobile); 
            employeeRepository.addEmployee(employee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException ("No such department" + departmentId, e);
        }     
    }

    public void removeEmployee(int id) throws IllegalArgumentException, 
                                               EmployeeException {
        try {
            Employee employee = employeeRepository.findEmployeeById(id);
            if (employee != null) {
                employeeRepository.deleteEmployee(id);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee not found" + id, e);
        }
    }

    public List<Employee> getAllEmployees() throws EmployeeException {
        return employeeRepository.getAllEmployees();
    }

    public Employee getEmployeeById(int id) throws EmployeeException {
        return employeeRepository.findEmployeeById(id);
    }        

    public void updateEmployee(int id, String name, LocalDate dob,
                               String emailId, String mobile, int departmentId)
                               throws IllegalArgumentException, EmployeeException {
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
            throw new IllegalArgumentException("Employee not found" + id, e);
        }
    }

    public Department getDepartmentById(int id) throws EmployeeException {
        return departmentService.getDepartmentById(id);
    }

    public List<Department>getAllDepartment() throws EmployeeException {
       return departmentService.getAllDepartments();
    }

    public void addProjectToEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, EmployeeException {

        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.addProjectToEmployee(employeeId, projectId);
        
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + employeeId, e);
        }
    }

    public void removeProjectFromEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, EmployeeException {
        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.removeProjectFromEmployee(employeeId, projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + employeeId, e);
        }
    }

    public List<Project> getAllProjects() throws EmployeeException {
        return projectService.getAllProjects();
    }
}

