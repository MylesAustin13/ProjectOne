package DAOs;

import Entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;


public class EmployeeDaoImpl implements  EmployeeDao{

    @Override
    public void addEmployee(Employee e) {
        //Create the Config object
        Configuration cfg = new Configuration();

        //Read the config file and load it into the Config object
        cfg.configure("hibernate.cfg.xml");

        //Create the Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        //Use the factory to create a Session
        Session session = sessionFactory.openSession();

        //Start the transaction
        Transaction t = session.beginTransaction();

        //Save the Employee object
        session.save(e);

        //Commit the transaction
        t.commit();

        //Close the connection
        session.close();
    }

    @Override
    public Employee getEmployeeByUsername(String uname) {
        //Create the Config object
        Configuration cfg = new Configuration();
        //Read the config file and load it into the Config object
        cfg.configure("hibernate.cfg.xml");
        //Create the Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        //Use the factory to create a Session
        Session session = sessionFactory.openSession();


        //Set up a query, used named parameters for safety
        //Source: https://www.tutorialspoint.com/hibernate/hibernate_query_language.htm

        String hql = "from Employee e where e.username = :employee_name";
        Query query = session.createQuery(hql);
        query.setParameter("employee_name", uname);
        List<Employee> employees = query.getResultList();

        //Close the connection
        session.close();

        if (employees.isEmpty()){ //The results are empty, found no employee
            return null;
        }
        else{  //Results not empty, found at least one employee
            return employees.get(0); //Get the first one
        }
    }

    @Override
    public Employee getEmployeeByID(int empl_id) {
        //Create the Config object
        Configuration cfg = new Configuration();

        //Read the config file and load it into the Config object
        cfg.configure("hibernate.cfg.xml");

        //Create the Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        //Use the factory to create a Session
        Session session = sessionFactory.openSession();


        //Set up a query, used named parameters for safety
        //Source: https://www.tutorialspoint.com/hibernate/hibernate_query_language.htm

        String hql = "from employee e where e.empl_id = :employee_id";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id", empl_id);
        List<Employee> employees = query.getResultList();

        //Close the connection
        session.close();

        if (employees.isEmpty()){ //The results are empty, found no employee
            return null;
        }
        else{  //Results not empty, found at least one employee
            return employees.get(0); //Get the first one
        }


    }

    @Override
    public List<Employee> getAllEmployees() {
        //Create the Config object
        Configuration cfg = new Configuration();

        //Read the config file and load it into the Config object
        cfg.configure("hibernate.cfg.xml");

        //Create the Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        //Use the factory to create a Session
        Session session = sessionFactory.openSession();


        //Set up a query to get the employees????
        //Source: https://www.baeldung.com/hibernate-select-all
        List<Employee> employees = session.createQuery("select a from Employee a", Employee.class).getResultList();




        //Close the connection
        session.close();
        return employees;
    }
}
