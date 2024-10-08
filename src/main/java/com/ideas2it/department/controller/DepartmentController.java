package com.ideas2it.department.controller;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ideas2it.utils.EmployeeValidator;
import com.ideas2it.department.service.DepartmentService;
import com.ideas2it.department.service.DepartmentServiceImpl;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.EmployeeException;

/**
* <h1>Department Controller</h1>
* <p>
* Handling all the department related inputs from user and outputs to
* display, department must be added before adding employee.
* </p>
*/
public class DepartmentController {
    private DepartmentService departmentService  = new DepartmentServiceImpl();
    private EmployeeValidator employeeValidator;
	private static Logger logger = LogManager.getLogger(DepartmentController.class);
    private static int idCounter = 1; 
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the Department management menu and handles user choices.
     */
    public void displayDepartmentManagement() {
		try {
			while (true) {
				System.out.println("Department Management");
				System.out.println("-------------------------------");
				System.out.println("1) Add Department");
				System.out.println("2) Delete Department");
				System.out.println("3) Display All Departments");
				System.out.println("4) Update Department");
				System.out.println("5) Department Based Employee"); 
				System.out.println("6) Back to Main Menu");
				System.out.println("-------------------------------");
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();
                scanner.nextLine();
				switch (choice) {
					case 1:
						addDepartment();
						break;
					case 2:
						deleteDepartment();
						break;
					case 3:
						displayAllDepartments();
						break;
					case 4:
						updateDepartment();
						break;
					case 5:
						displayEmployeesInDepartment();
						break;
					case 6:
						return;
					default:
						System.out.println("Invalid choice.");
				}
			}
        } catch (InputMismatchException e) {
			logger.error("Please Enter a Valid Option (Numeric only)");
            scanner.next();
		}
    }

    /**
    *<p>
    * Method which allows to add departments along with department Id.
    * It checks whether Id is in database, if not in database then it adds
    *</p>
    */
    public void addDepartment() {
        try {
            String name = "";
            boolean checkName = false;
            scanner.nextLine();
            while (!checkName) {
                System.out.print("Enter Department Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                    System.out.println("InValid Name Entered");
                } else {
                    checkName = true;
                }
            }
            departmentService.addDepartment(idCounter, name);
            logger.info("Department added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (EmployeeException e) {
            logger.error("Error while adding department", e);
        }
    }

    /**
    * <p>
    * deletes the department using the departmentId.
    * </p>
    */
    public void deleteDepartment() {
        System.out.print("Enter Department ID: ");
        int id = scanner.nextInt();
        try {
            departmentService.removeDepartment(id);
            System.out.println("Department deleted successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (EmployeeException e) {
            logger.error("Error while deleting department", id, e);
        }
    }

    /**
    * <p>
    * display all departments in database and it is called during the add
    * employee method to show and get input from user.
    * </p>
    */
    public void displayAllDepartments() {
        try {
            System.out.printf("%-10s %-20s\n","ID","Department Name");
            System.out.println("------------------------------");
            departmentService.getAllDepartments().forEach(System.out::println);
            System.out.println("------------------------------");
        } catch (EmployeeException e) {
            logger.error("No department found", e);
        }
    }

/**
 * prompts the user to enter details for an existing department ID and updates its information.
 */
    public void updateDepartment() {
        System.out.print("Enter the ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            departmentService.updateDepartment(id, name);
            logger.info("Department updated successfully.");
        } catch (IllegalArgumentException e) {
            logger.error("No department found", e.getMessage());
        } catch (EmployeeException e) {
            logger.error("Error while updating department", id, e);
        }
    }

    /**
     * <p>
     * User to enter the ID of a department and displays the employees
     * in it.
     * </p>
     */
    public void displayEmployeesInDepartment() {
        displayAllDepartments();
        System.out.println("enter Department ID: ");
        int id=scanner.nextInt();
        scanner.nextLine();
        try {
             List<Employee> employees = departmentService.getEmployeesByDepartmentId(id);
             System.out.printf("Employees in Department %d:\n",id);
             System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s\n", "EmployeeID", "Name", "Age", "Department", "Email", "Mobile");
             System.out.println("-----------------------------------------------------------------------------------------------------------------");
             for(Employee employee : employees) {
                 System.out.println(employee);
             }
             System.out.println("-----------------------------------------------------------------------------------------------------------------");
         } catch(IllegalArgumentException e){
              logger.error(e.getMessage());
         } catch (EmployeeException e) {
            logger.error("Department not found with this ID : " , id, e);
        }
    }
}
