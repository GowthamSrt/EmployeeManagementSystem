package com.ideas2it.project.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ideas2it.model.Project;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.DatabaseException;
import com.ideas2it.utils.HibernateConnection;


public class ProjectRepositoryImpl implements ProjectRepository {

    public void addProject(Project project) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			session.save(project);
			transaction.commit();     

        } catch (HibernateException e) {
			if(transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException ("Error while adding project" + project.getName());
        } finally {
            session.close();
        }
    }

    public void updateProject(Project project) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			Project existingProject = session.get(Project.class, project.getId());
			if (existingProject != null) {
			    existingProject.setName(project.getName());
				session.update(existingProject);
				transaction.commit();
			}
			else {
				throw new DatabaseException("No project found with ID : " + project.getId());
			}
        } catch (HibernateException e) {
            throw new DatabaseException ("Error while updating project : " + project.getId());
        } finally {
            session.close();
        }
    }

    public void deleteProject(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			 Project project = session.get (Project.class, id);
             if (project != null) {
                 session.delete(project);             
                 transaction.commit();
             }
             else {
                 throw new DatabaseException("Project not found!!!  " + id);
             }

        } catch (HibernateException e) {
             throw new DatabaseException ("No project found  " + id);
        }  finally {
            session.close();
        } 
    }

    public List<Project> getAllProjects() throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        List<Project> projects = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            projects = session.createQuery("from Project",Project.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Unable to show projects" + e);
        } finally {
            session.close();
        }
        return projects;
    }

    public Project findProjectById(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        try {
            Project project = session.get(Project.class, id);
			if (project != null) {
			    return project;
			}
			else {
			    throw new DatabaseException("Project not found in this ID : " + id);
			}
            
        } catch (HibernateException e) {
            throw new DatabaseException ("No project found  " + id);
        } finally {
            session.close();
        }
    }

    public Project mapRowToProject(int id, String name) throws DatabaseException {
		Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
		Project project = null;
        try {
			transaction = session.beginTransaction();
			project = session.get(Project.class, id);
			if (project == null) {
			    throw new DatabaseException("No project found with ID : " + id);
			}
			
			project.setName(name);
        } catch (HibernateException e) {
			if (transaction != null) {
			    transaction.rollback();
			}
            throw new DatabaseException ("No project found  " + id);
        } finally {
			session.close();
		}
		return project;
    }

    public List<Employee> getEmployeesByProjectId(int projectId) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;

        try {
			transaction = session.beginTransaction();
			String hqlQuery = "select e from Employee e join e.projects p where p.id = :projectId and e.isActive = true";
			Query<Employee> query = session.createQuery(hqlQuery, Employee.class);
			query.setParameter("projectId", projectId);
			employees = query.list();
			transaction.commit();
         } catch (HibernateException e) {
			 if (transaction != null) {
			     transaction.rollback();
			 }
             throw new DatabaseException ("No employee found in this project with ID : " + projectId);
         } finally {
             session.close();
         }
        return employees;
    }
}
