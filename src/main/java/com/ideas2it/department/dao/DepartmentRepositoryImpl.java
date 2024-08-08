package com.ideas2it.department.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.EmployeeException;
import com.ideas2it.utils.HibernateConnection;

public class DepartmentRepositoryImpl implements DepartmentRepository  {
    private Department department;
    private static Logger logger = LogManager.getLogger(DepartmentRepositoryImpl.class);

    public void addDepartment(Department department) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            int id = (Integer) session.save(department);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while adding department");
           throw new EmployeeException("Error while adding department" + department.getName(), e);
        }
     }
	 
    public void updateDepartment(Department department) throws EmployeeException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Department existingDepartment = session.get(Department.class, department.getId());
            if (existingDepartment != null) {
                existingDepartment .setName(department.getName());
                session.update(existingDepartment );
                transaction.commit();
            }
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't able to update the department ");
            throw new EmployeeException("Error while updating department : " + department.getId(), e);
        }
    }
	
     public void deleteDepartment(int id) throws EmployeeException {
         Transaction transaction = null;
         try (Session session = HibernateConnection.getFactory().openSession()) {
             transaction = session.beginTransaction();
             Department department = session.get (Department.class, id);
             if (department != null) {
                 session.delete(department);             
                 transaction.commit();
             }
             else {
                 throw new EmployeeException("Department not found : " + id);
             }
         } catch (HibernateException e) {
             if(transaction != null) {
                transaction.rollback();
            }
             logger.error("No Department found with this ID");
             throw new EmployeeException("Error while deleting department : " + id, e);
         }
     }

     public List<Department> getAllDepartments() throws EmployeeException {
        List<Department> departments = new ArrayList<>();
        try (Session session = HibernateConnection.getFactory().openSession()) {
            departments = session.createQuery("from Department",Department.class).list();
        } catch (HibernateException e) {
            logger.error("No Departments tp display");
            throw new EmployeeException("Unable to show departments", e);
        }
        return departments;
    }

    public Department findDepartmentById(int id) throws EmployeeException {
        Department department = null;
        try (Session session = HibernateConnection.getFactory().openSession()) {
			department = session.get(Department.class, id);
			if (department == null) {
			    throw new EmployeeException("No Department found in this ID : " + id);
			}
        } catch (HibernateException e) {
            logger.error("No department found in this ID");
            throw new EmployeeException("Department not found" + id, e);
        }
        return department;
    }

    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
		try (Session session = HibernateConnection.getFactory().openSession()) {
			String hqlQuery = "from Employee e left join fetch e.department d "
										   + "where d.id = :departmentId";
			Query<Employee> query = session.createQuery(hqlQuery, Employee.class);
			query.setParameter("departmentId", departmentId);
			employees = query.getResultList();
        } catch (HibernateException e) {
            logger.error("There is no employee to display");
            throw new EmployeeException("No Employees to display in this DepartmentID : " + departmentId, e);
        }
        return employees;
    }
}