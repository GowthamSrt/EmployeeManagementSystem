package com.ideas2it.project.dao;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.department.dao.DepartmentRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ideas2it.model.Project;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.EmployeeException;
import com.ideas2it.utils.HibernateConnection;


public class ProjectRepositoryImpl implements ProjectRepository {
    private static Logger logger = LogManager.getLogger(DepartmentRepositoryImpl.class);

    public void addProject(Project project) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding the project");
            throw new EmployeeException("Error while adding project: " + project.getName(), e);
        }
    }


    public void updateProject(Project project) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Project existingProject = session.get(Project.class, project.getId());
            existingProject.setName(project.getName());
            session.update(existingProject);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating the project");
            throw new EmployeeException("Error while updating project : " + project.getId(), e);
        }
    }

    public void deleteProject(int id) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Project project = session.get(Project.class, id);
            session.delete(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting the project.");
            throw new EmployeeException("No project found  " + id, e);
        }
    }

    public List<Project> getAllProjects() throws EmployeeException {
        List<Project> projects = new ArrayList<>();
        try (Session session = HibernateConnection.getFactory().openSession()) {
            projects = session.createQuery("from Project", Project.class).list();
        } catch (HibernateException e) {
            logger.error("No projects Available to show");
            throw new EmployeeException("Unable to show projects", e);
        }
        return projects;
    }

    public Project findProjectById(int id) throws EmployeeException {
        try (Session session = HibernateConnection.getFactory().openSession()) {
            Project project = session.get(Project.class, id);
            if (project != null) {
                return project;
            } else {
                throw new EmployeeException("Project not found in this ID : " + id);
            }
        } catch (HibernateException e) {
            throw new EmployeeException("No project found : " + id, e);
        }
    }

    public Project mapRowToProject(int id, String name) throws EmployeeException {
        Project project = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            project = session.get(Project.class, id);
            project.setName(name);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("No project found error");
            throw new EmployeeException("No project found : " + id, e);
        }
        return project;
    }

    public List<Employee> getEmployeesByProjectId(int projectId) throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
        try (Session session = HibernateConnection.getFactory().openSession()) {
            String hqlQuery = "select e from Employee e join e.projects p where p.id = :projectId and e.isActive = true";
            Query<Employee> query = session.createQuery(hqlQuery, Employee.class);
            query.setParameter("projectId", projectId);
            employees = query.list();
        } catch (HibernateException e) {
            logger.error("No employee found");
            throw new EmployeeException("No employee found in this project with ID : " + projectId);
        }
        return employees;
    }
}
