package com.ideas2it.department.service;

import java.util.List;

import com.ideas2it.department.dao.DepartmentRepositoryImpl;
import com.ideas2it.department.service.DepartmentServiceImpl;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.exception.DatabaseException;

/**
* <p>
* Class implements the services related with department management and there
* should be no null values provided.
* </p>
*/
public interface DepartmentService {

    /**
    * <p>
    * Method adds the department into the database which must have Id and Name
    * and it should not be left blank or empty.
    * </p>
    * @param id denotes the id of department
    * @param name denotes the name.
    */
    public void addDepartment(int id, String name) throws DatabaseException;

     /**
    * <p>
    * To delete the department, only if there is no employees in that 
    * particular department else it'll not accept.
    * </p>
    */
    public void removeDepartment(int id) throws IllegalArgumentException, DatabaseException;

    /**
    * <p>
    * To get all the departments to display while selecting the particular
    * deoartment to a employee.
    * </p>
    */ 
    public List<Department> getAllDepartments() throws DatabaseException;

    /**
    * <p>
    * To get the department by Id to make the operations needed.
    * </p>
    */
    public Department getDepartmentById(int id) throws IllegalArgumentException, DatabaseException;

    /**
    * <p>
    * Method will update the existing and be sure to update the existing
    * department.
    * </p>
    */
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DatabaseException;
    
	/**
	 * <p>
     * Finds employees by department ID.
	 * </p>
     * @param Id - deparmnetID of the department.
     * @return list employee.
     */
    public List<Employee> getEmployeesByDepartmentId(int id) throws DatabaseException;

}
