package com.ideas2it.project.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.model.Project;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Department;
import com.ideas2it.exception.EmployeeException;

public interface ProjectRepository {
    /**
    * <p>
    * Method which adds the details of projects taken from controller.
    * </p>
    * *@param projectName
    */
    public void addProject(Project project) throws EmployeeException;

    /**
    * <p>
    * Delete the the project as per user request if and only if there is
    * no employee is assigned to that particular project.
    * </p>
    */
    public void deleteProject(int id) throws EmployeeException;

    /**
    * <p>
    * Returns the all the projectId and projectName from the list that we have
    * currently in.
    * </p>
    */
    public List<Project> getAllProjects() throws EmployeeException;

    /**
     * To finds a project by ID from the database.
     * @param id - project to find.
     * @return The project if found, null otherwise.
     */
    public Project findProjectById(int id) throws EmployeeException;

    /**
    * <p>
    * Method which updates the details of project taken from controller and
    * here updation only done if the project is already exist.
    * </p>
    */
    public void updateProject(Project project) throws EmployeeException;

    /**
     * Maps a row from the resultSet to an project object.
     * @param Name The ResultSet containing the data.
     */
    public Project mapRowToProject(int id, String Name) throws SQLException, EmployeeException;

    /**
     * Retrives the employee association with an projects by their id.
     * @param projectId - Id of project.
     * @return A list of employee association with projects.
     */
    public List<Employee> getEmployeesByProjectId(int projectId) throws EmployeeException;
}

    