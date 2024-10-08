import java.util.InputMismatchException;
import java.util.Scanner;

import com.ideas2it.employee.controller.EmployeeController;
import com.ideas2it.department.controller.DepartmentController;
import com.ideas2it.project.controller.ProjectController;


/**
 * <p>
 * The main class for the Employee Management System. This class contains the main method
 * which starts the application and provides a menu for navigating between different management
 * functionalities.
 * </p>
 */
public class EmployeeManagement {
    public static void main(String[] args) {

        EmployeeController employeeController = new EmployeeController();
        DepartmentController departmentController = new DepartmentController();
        ProjectController projectController = new  ProjectController();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println(" ___Employee Management System___ ");
			System.out.println("Select the Management Menu");
			System.out.println("1) Employee Management");
			System.out.println("2) Department Management");
			System.out.println("3) Project Management");
			System.out.println("4) Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			try {
				switch (choice) {
				case 1:
						employeeController.displayEmployeeManagement();
						break;
					case 2:
						departmentController.displayDepartmentManagement();
						break;
					case 3:
						projectController.displayProjectManagement();
						break;
					case 4:
						System.out.println("Exiting...!");
						System.exit(0);
					default:
						System.out.println("Invalid choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please Enter a Valid Option");
                scanner.next();
			}
		}
	}
}
