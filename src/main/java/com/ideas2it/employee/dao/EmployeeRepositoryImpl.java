package com.ideas2it.employee.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ideas2it.exception.DatabaseException;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Project;
import com.ideas2it.utils.HibernateConnection;


public class EmployeeRepositoryImpl implements EmployeeRepository {

    public void addEmployee(Employee employee) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
			session.save(employee);
			transaction.commit();
        } catch (HibernateException e) {
			if (transaction != null) {
			    transaction.rollback();
			}
            throw new DatabaseException("Error while adding to Database  :  " + e);
			
        } finally {
            session.close();
        }
    }

    public void deleteEmployee(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
			Employee employee = session.get(Employee.class, id);
			if (employee != null) {
			    employee.setIsActive(false);
				session.update(employee);
				transaction.commit();
			}
			else {
			    throw new DatabaseException("No Employee found in ID : " + id);
			}
        } catch (HibernateException e) {
            throw new DatabaseException("Not able to delete  "+ e + "  " + id);
        } finally {
             session.close();
        }
    }

    public List<Employee> getAllEmployees() throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        Session session = HibernateConnection.getFactory().openSession();
		Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			String hql = "from Employee where isActive = true";
			Query<Employee> query = session.createQuery(hql, Employee.class);
			employees = query.list();
			transaction.commit();
        } catch (HibernateException e) {
			if(transaction != null) {
                transaction.rollback();
		  System.out.println(e.getMessage());
            }
            throw new DatabaseException("Error while getting " + e.getMessage());
        } finally {
             session.close();
        }
		return employees;
    }

    public Employee findEmployeeById(int id) throws DatabaseException {
		Session session = HibernateConnection.getFactory().openSession();
		Transaction transaction = null;
        Employee employee = null;        
        try {
			transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery( "From Employee e "
			                        + "left join fetch e.department where e.id = :id "
									+ "and e.isActive = true", Employee.class);
			query.setParameter("id", id);
			employee = query.uniqueResult();
			transaction.commit();
         
         } catch (HibernateException e) {
             throw new DatabaseException("Error while finding Employee with ID : " + id);
         } finally {
             session.close();
         }
		 return employee;
    }

    public void updateEmployee(Employee employee) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
			session.update(employee);
			transaction.commit();
           
        } catch (HibernateException e) {
			if (transaction != null) {
			    transaction.rollback();
			}
            throw new DatabaseException("Error while updating employee with ID" + employee.getId());
        } finally {
             session.close();
        }
    }

    public void addProjectToEmployee(int employeeId, int projectId)
                                           throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeId);
			Project project = session.get(Project.class, projectId);
			
			if (employee == null || project == null) {
			    throw new DatabaseException("Employee or Project not found with given ID");
			}
			employee.getProjects().add(project);
			project.getEmployees().add(employee);
			session.update(employee);
			session.update(project);
			transaction.commit();
        } catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
            throw new DatabaseException("Error while adding project : " + e.getMessage() + "  " + e);
        } finally {
             session.close();
        }
    } 

    public void removeProjectFromEmployee(int employeeId, int projectId)
                                           throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeId);
			Project project = session.get(Project.class, projectId);
			
			if (employee == null || project == null) {
			    throw new DatabaseException("Employee or Project not found with given ID");
			}
			if (employee.getProjects().contains(project)){
				employee.getProjects().remove(project);
				session.update(employee);
			} else {
				throw new DatabaseException("This project is not associated with the employee");
			}
			transaction.commit();
        } catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
            throw new DatabaseException("Error while removing project from employee : " + e.getMessage() + "  " + e);
        } finally {
             session.close();
        }
    }
}
       

    
