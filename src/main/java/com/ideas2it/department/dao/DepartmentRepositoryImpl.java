package com.ideas2it.department.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException; 
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.model.Department;
import com.ideas2it.model.Employee;
import com.ideas2it.exception.DatabaseException;
import com.ideas2it.utils.HibernateConnection;

public class DepartmentRepositoryImpl implements DepartmentRepository  {
    private Department department;
    public void addDepartment(Department department) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int id = (Integer) session.save(department);
            transaction.commit();
        } catch (HibernateException e) {
           throw new DatabaseException("Error while adding department" + e);
        } finally {
            session.close();
        }
     }
	 
    public void updateDepartment(Department department) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
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
            throw new DatabaseException("Error while updating department  " + department.getId() + "  " + e);
        } finally {
            session.close();
        }
    }
	
     public void deleteDepartment(int id) throws DatabaseException {
         Session session = HibernateConnection.getFactory().openSession();
         Transaction transaction = null;
         try {
             transaction = session.beginTransaction();
             Department department = session.get (Department.class, id);
             if (department != null) {
                 session.delete(department);             
                 transaction.commit();
             }
             else {
                 throw new DatabaseException("Department not found!!!  " + id);
             }
         } catch (HibernateException e) {
             if(transaction != null) {
                transaction.rollback();
            }
             throw new DatabaseException("Error while deleting department  " + id + "  " + e);
             
         } finally {
             session.close();
        }
     }

     public List<Department> getAllDepartments() throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        List<Department> departments = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            departments = session.createQuery("from Department",Department.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Unable to show departments" + e);
        } finally {
            session.close();
        }
        return departments;
    }

    public Department findDepartmentById(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        Department department = null;
        try {
			transaction = session.beginTransaction();
			department = session.get(Department.class, id);
			if (department == null) {
			    throw new DatabaseException("No Department found in this ID : " + id);
			}
			transaction.commit();
        } catch (HibernateException e) {
            throw new DatabaseException("Department not found" + id);
        } finally {
            session.close();
        }
        return department;
    }

    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
		Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hqlQuery = "from Employee e left join fetch e.department d "
										   + "where d.id = :departmentId";
			Query<Employee> query = session.createQuery(hqlQuery, Employee.class);
			query.setParameter("departmentId", departmentId);
			employees = query.getResultList();
			transaction.commit();
        } catch (HibernateException e) {
			if (transaction != null) {
			    transaction.rollback();
			}
            throw new DatabaseException("No Employees to display" + e);
        } finally {
            session.close();
        }
        return employees;
    }
}