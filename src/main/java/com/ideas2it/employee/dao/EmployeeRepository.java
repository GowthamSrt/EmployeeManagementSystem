package com.ideas2it.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.ideas2it.model.Employee;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.exception.EmployeeException;

/**
* <p> 
* class implements the employee database operations od add, update, delete and 
* to display all the employee details from the database.
* </p>
*/
public interface EmployeeRepository {
    /**
    * <p>
    * Method which adds the details of employee taken from controller.
    * </p>
    */
    public void addEmployee(Employee employee) throws EmployeeException;

    /**
    * <p>
    * Method which delete the details of employee by using the employee Id.
    * </p>
    */
    public void deleteEmployee(int id) throws EmployeeException;

    /**
    * <p>
    * Returns all the employees from the database.
    * </p>
    */
    public List<Employee> getAllEmployees() throws EmployeeException;

    /**
    * <p>
    * Method return a single employee by using the employee Id search.
    * </p>
    */
    public Employee findEmployeeById(int id) throws EmployeeException;

    /**
    * <p>
    * Method which updates the details of employee taken from controller by
    * Checking already exist or not.
    * </p>
    */
    public void updateEmployee(Employee employee) throws EmployeeException;

    /**
    * <p>
    * Adds a project to an employee by fetching the project and employee
    * id.
    * </p>
    */    
    public void addProjectToEmployee(int employeeId, int projectId) throws EmployeeException;

    /**
     * <p>
     * Remove a project to an employee by fetching the project and employee
     * id.
     * </p>
     */ 
    public void removeProjectFromEmployee(int employeeId, int projectId) throws EmployeeException;

}