package com.ideas2it.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ideas2it.exception.DatabaseException;
/**
* <p>
* This class is responsible for configuring and providing access to
* the Hibernate SessionFactory, which in turn manages the database
* sessions and transactions.
* </p>
* 
* @author Gowthamraj
*/

public class HibernateConnection {
    private static SessionFactory factory = null;

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            } catch (HibernateException e) {
                System.out.println("Failed to connect to SessionFactory");
            }
        }
        return factory;
    }

    public static void closeFactory() throws DatabaseException {
        try {
            factory.close();
        } catch (HibernateException e) {
            throw new DatabaseException("Factory not closed!!" + e);
        }
    }
}