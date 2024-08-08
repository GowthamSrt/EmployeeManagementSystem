package com.ideas2it.department.dao;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.EmployeeException;

public interface DepartmentRepository {
   /**
    * <p>
    * method which adds the departmentId and name to the database with 
    * checking whether departmentId alreday available or not.
    * </p>
    * @param department
    */
    public void addDepartment(Department department) throws EmployeeException;

    /**
    * <p>
    * hanldes the updation of department by checking whether department already
    * added and then it permits for updation.
    * </p>
    */
    public void updateDepartment(Department department) throws EmployeeException;

    /**
    * <p>
    * method deletes the department from the database by getting Id from user
    * </p>
    */
    public void deleteDepartment(int id) throws EmployeeException;

    /**
    * <p>
    * display all the departments in a table of Id and name of departments.
    * </p>
    */
    public List<Department> getAllDepartments() throws EmployeeException;

    /**
    * <p>
    * method to filter out the department using the department ID to view the
    * employees in a specific department.
    * </p>
    */
    public Department findDepartmentById(int id) throws EmployeeException;

    /**
    * <p>
    * Get all the departments that are currently available in the database.
    * </p>
    */
    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws EmployeeException;

}