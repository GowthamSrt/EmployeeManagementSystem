package com.ideas2it.employee.dao;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.department.dao.DepartmentRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.exception.EmployeeException;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Project;
import com.ideas2it.utils.HibernateConnection;


public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static Logger logger = LogManager.getLogger(DepartmentRepositoryImpl.class);

    public void addEmployee(Employee employee) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error by adding employee");
            throw new EmployeeException("Error while adding to Database : " + employee, e);
        }
    }

    public void deleteEmployee(int id) throws EmployeeException {

        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            employee.setIsActive(false);
            session.update(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error by deleting the employee");
            throw new EmployeeException("Not able to delete employee : " + id, e);
        }
    }

    public List<Employee> getAllEmployees() throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
        try (Session session = HibernateConnection.getFactory().openSession()) {
            String hql = "from Employee where isActive = true";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            employees = query.list();
        } catch (HibernateException e) {
            logger.error("Unable to display all the employees");
            throw new EmployeeException("Error while getting ", e);
        }
        return employees;
    }

    public Employee findEmployeeById(int id) throws EmployeeException {
        Employee employee = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            Query<Employee> query = session.createQuery("From Employee e "
                    + "left join fetch e.department where e.id = :id "
                    + "and e.isActive = true", Employee.class);
            query.setParameter("id", id);
            employee = query.uniqueResult();
        } catch (HibernateException e) {
            logger.error("Unable to display that particular employee");
            throw new EmployeeException("Error while finding Employee with ID : " + id, e);
        }
        return employee;
    }

    public void updateEmployee(Employee employee) throws EmployeeException {

        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error by updating the employee details");
            throw new EmployeeException("Error while updating employee with ID" + employee.getId(), e);
        }
    }

    public void addProjectToEmployee(int employeeId, int projectId)
            throws EmployeeException {

        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Project project = session.get(Project.class, projectId);
            if (employee == null || project == null) {
                throw new EmployeeException("Employee or Project not found with given ID");
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
            logger.error("Error while assigning project to employee", employeeId);
            throw new EmployeeException("Error while adding project : " + e.getMessage(), e);
        }
    }

    public void removeProjectFromEmployee(int employeeId, int projectId)
            throws EmployeeException {

        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Project project = session.get(Project.class, projectId);
            if (employee == null || project == null) {
                throw new EmployeeException("Employee or Project not found with given ID");
            }
            if (employee.getProjects().contains(project)) {
                employee.getProjects().remove(project);
                session.update(employee);
            } else {
                throw new EmployeeException("This project is not associated with the employee" + employeeId);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while removing project from employee", employeeId);
            throw new EmployeeException("Error while removing project from employee : " + employeeId, e);
        }
    }
}
       

    
