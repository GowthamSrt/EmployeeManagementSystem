package com.ideas2it.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Project;
import com.ideas2it.exception.DatabaseException;

/**
 *<p>
 * Interface for employeeservice  to handle  employee-related operation.
 *</p>
 *
 */
public interface EmployeeService {

    /**
     * <p>
     * Add all the parameter passed through the controller into the database
     * and makes the it ready at all for the end users.
     * Here all the parameters are only passed through this layer.
     * </p>
     */
    void addEmployee(int id, String name, LocalDate dob, String emailId,
                            String mobile, int deptId) throws IllegalArgumentException, DatabaseException;
    /**
     * <p>
     * Delete the employee parameters by getting the employee id which
     * needs to be deleted and employee id should existing one.
     * </p>
     */
    void removeEmployee(int id) throws IllegalArgumentException, DatabaseException;

    /**
     * <p>
     * returns all the employee from the database and hereafter it is
     * filtered as per the user request.
     * </p>
     */
    List<Employee> getAllEmployees() throws DatabaseException;

    /**
     * <p>
     * Getting an employee using the employee Id to show their profile alone
     * from the database.
     * </p>
     */
    Employee getEmployeeById(int id) throws DatabaseException;

    /**
     * <p>
     * Upadte all the parameter passed through the controller into the database
     * with all the validations same on add method. And before updating select
     * an existing department in database.
     * </p>
     */
    void updateEmployee(int id, String name, LocalDate dob,
                                String emailId, String mobile, int deptId) throws IllegalArgumentException, DatabaseException;
    /**
     * <p>
     * Getting an employee using the department Id to show their profile alone
     * from the database.
     * </p>
     */
    Department getDepartmentById(int id) throws DatabaseException;

    /**
     * <p>
     * Returns all the department list which is used to show the available
     * departments while assigning the department to an employee.
     * </p>
     */
    List<Department>getAllDepartment() throws DatabaseException;

    /**
     * <p>
     * Adds project to an employee to mention that employee is working in
     * which project.
     * </p>
     */
    void addProjectToEmployee(int employeeId, int projectId) throws IllegalArgumentException, DatabaseException;

    /**
     * <p>
     * Removes project from employee by using the employee and project id
     * </p>
     */
    void removeProjectFromEmployee(int employeeId, int projectId) throws IllegalArgumentException, DatabaseException;

    /**
     * <p>
     * Returns all the projects from database and it is shown as Current project
     * which is used to assign single or multiple projects to an employee.
     * </p>
     */
    public List<Project> getAllProjects() throws DatabaseException;
}
