package DAOs;

import Entities.Employee;
import Entities.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;

public class ManagerDaoImpl implements ManagerDao{

    @Override
    public void addManager(Manager m) {
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

        //Save the Manager object
        session.save(m);

        //Commit the transaction
        t.commit();

        //Close the connection
        session.close();
    }

    @Override
    public Manager getManagerByUsername(String uname) {
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

        String hql = "from Manager m where m.username = :manager_name";
        Query query = session.createQuery(hql);
        query.setParameter("manager_name", uname);
        List<Manager> managers = query.getResultList();

        //Close the connection
        session.close();

        if (managers.isEmpty()){ //The results are empty, found no manager
            return null;
        }
        else{  //Results not empty, found at least one manager
            return managers.get(0); //Get the first one
        }
    }
}
