package com.ideas2it.employee.controller;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.employee.service.EmployeeServiceImpl;
import com.ideas2it.exception.EmployeeException;
import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Project;
import com.ideas2it.utils.EmployeeValidator;

/**
* <h1>Employee Controller</h1>
* This program implements an application that used to manage the employee
* records with some validations & logics and returns the outputs
* as per the request. 
* <p>
* @author  Gowthamraj
*/
public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final EmployeeValidator employeeValidator = new EmployeeValidator();
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static int idCounter = 1;
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private Scanner scanner = new Scanner(System.in);

    /**
     * To make the user choice and navigating through the selection. 
     */
    public void displayEmployeeManagement() {
		try {
			while (true) {
				System.out.println("-------------------------------------");
				System.out.println("     Employee Management System      ");
				System.out.println("-------------------------------------");
				System.out.println("1) Add Employee");
				System.out.println("2) Update Employee");
				System.out.println("3) Display All Employees");
				System.out.println("4) Display Employee by ID");
				System.out.println("5) Delete Employee");
				System.out.println("6) Add Project To Employee");
				System.out.println("7) Remove Project From Employee");
				System.out.println("8) Back to Main Menu");
				System.out.println("-------------------------------------");
				System.out.println("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				switch (choice) {
					case 1:
						addEmployee();
						break;
					case 2:
						updateEmployee();
						break;
					case 3:
						displayAllEmployees();
						break;
					case 4:
						displayEmployeeById();
						break;
					case 5:
						deleteEmployee();
						break;
					case 6:
						addProjectToEmployee();
						break;
					case 7:
						removeProjectFromEmployee();
						break;
					case 8:
						return;
					default:
						System.out.println("Invalid choice.");
				}
        }
            } catch (InputMismatchException e) {
				logger.error("Please Enter a Valid Option 12345678");
                scanner.next();
			}
    }

    /**
     * Prompts the user to add a new employee into database.
     * please add at least one department to add employees.
     * It can be even empty & later it can be updated.
     * </p>
     */
    public void addEmployee() {
        try {
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            LocalDate dob = null;
            String name = "";
            String email = "";

            while(!checkName){
                System.out.println("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                System.out.println("InValid Name Entered");
                } else {
                    checkName = true;
                }
             }
            while(!checkDob){
                System.out.println("Enter employee DOB (dd-MM-yyyy): ");
				try {
					dob = LocalDate.parse(scanner.nextLine(),dateFormat);
					int age = Period.between(dob, LocalDate.now()).getYears();
                if (age < 18 || age > 65) {
                    throw new IllegalArgumentException("Age should be"
	                                  +" between 18 and 65.");
                } 
                else {
                    checkDob = true;
                }
				} catch (DateTimeParseException e) {
					System.out.println("Invalid Date Format. Date format is dd-MM-yyyy");
				}
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid email format");
                } else {
                    checkEmail = true;
                }
            }
            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
				System.out.println("Invalid Mobile number format");
                return;
            }
            scanner.nextLine();
            for(Department departments : employeeService.getAllDepartment()){
               System.out.println("Department ID= " + departments.getId() 
                                  + "   " + "Department Name = " 
                                  + "   " +departments.getName());
            }
            System.out.print("Enter department Id: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine();
            employeeService.addEmployee(idCounter, name, dob, email, mobile, departmentId);
            logger.info("Employee added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (EmployeeException e) {
            logger.error("Error while adding employee" + idCounter, e);
        }   
    }

     /**
    * <p>
    * Updating method which updates the record. All the add employee
    * criteria are applicable here to update the details.
    * </p>
    */
    public void updateEmployee() {
        System.out.print("Enter employee Id: ");
        int id = scanner.nextInt();
        try {
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            LocalDate dob = null;
            String name = "";
            String email = "";
            if(employeeService.getEmployeeById(id) == null) {
                System.out.println("Employee ID Not Found" + id);
                return;
            }
            scanner.nextLine();
            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                    System.out.println("InValid Name Entered");
                } else {
                    checkName = true;
                }
            }
            while(!checkDob){
                System.out.print("Enter employee DOB (dd-MM-yyyy): ");
				try {
					dob = LocalDate.parse(scanner.nextLine(),dateFormat);
					int age = Period.between(dob, LocalDate.now()).getYears();
                if (age < 18 || age > 65) {
                    throw new IllegalArgumentException("Age should be"
	                                  +" between 18 and 65.1");
                } 
                else {
                    checkDob = true;
                }
				} catch (DateTimeParseException e) {
					System.out.println("Date format is dd-MM-yyyy");
				}
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid email format");
                } else {
                    checkEmail = true;
                }
            }

            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
				System.out.println("Invalid Mobile number format");
                return;
            }
            
             for(Department departments : employeeService.getAllDepartment()){
               System.out.println("ID = " + departments.getId() + " name = " 
                                   + departments.getName());
            }

            System.out.print("Enter department Id: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine();
            employeeService.updateEmployee(id, name, dob, email, mobile, departmentId);
            logger.info("Employee updated successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)", e);
		} catch (EmployeeException e) {
             logger.error("Error while updating : " + id , e);
        }
    }

    /**
    * Making soft delete by id to hide those employee details from the request
    * by using a boolean value isActive.
    */
    public void deleteEmployee() {
            System.out.print("Enter Employee Id to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
        try {
            employeeService.removeEmployee(id);
            logger.info("Employee deleted successfully.");
        } catch (IllegalArgumentException e) {
            logger.error("");
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (EmployeeException e) {
            logger.error("Error while removing the employee" + id, e);
        }
    }
    
    /**
    *<p>
    * Displaying all the employee details based on a specified format
    * by using String format
    * </p>
    */
    public void displayAllEmployees() {
        try {
            System.out.printf("%-10s  %-20s  %-15s  %-20s  %-25s %-15s\n ","ID",
                            "Name","Age",  "Department Name", "Email", "Mobile No");
            System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
        } catch (EmployeeException e) {
            logger.error("No Employees to display!!!");
        }
    }
    
    /**
    * <p>
    * Displaying the employee by using employee Id to show all of their 
    * details from the database.
    *</p>
    */
    public void displayEmployeeById() {
            System.out.print("Enter Employee Id to display: ");
            int id = scanner.nextInt();
            scanner.nextLine();
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                System.out.printf("%-10s  %-20s  %-15s  %-20s  %-25s %-15s\n ",
                                  "ID", "Name","Age",  "Department Name",
                                  "Email", "Mobile No");
                System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
                System.out.println(employee);
                System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
            } else {
                System.out.println("Employee not found in this ID.");
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (EmployeeException e) {
            logger.error("No employee found" + e);
        }
    }

    /**
    * <p>
    * Add project to employee by fetching the project Id with employee Id.
    * </p>
    */
    public void addProjectToEmployee() {
        try {
            System.out.println("Enter Employee Id: ");
            int employeeId = scanner.nextInt();

            for (Project project : employeeService.getAllProjects()) {
            System.out.println("ID = " +project.getId()+ " Name = " +project.getName());
        }
            System.out.print("Enter Project Id: ");
            int projectId = scanner.nextInt();
            scanner.nextLine();

            employeeService.addProjectToEmployee(employeeId, projectId);
            logger.info("Project added to employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (EmployeeException e) {
            logger.error("No such project" + e);
        }
    }
    
    /**
    * <p>
    * Removes project from the employee who associated with that using both
    * employee and project id.
    *</p>
    */
    public void removeProjectFromEmployee() {
            System.out.print("Enter Employee Id: ");
            int employeeId = scanner.nextInt();
            System.out.print("Enter Project Id: ");
            int projectId = scanner.nextInt();
            scanner.nextLine();
        try {
            employeeService.removeProjectFromEmployee(employeeId, projectId);
            logger.info("Project removed from employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (EmployeeException e) {
            logger.error("No such project found in ID : " + projectId);
        }
    }
}
