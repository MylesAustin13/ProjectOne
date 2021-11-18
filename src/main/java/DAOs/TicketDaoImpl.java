package DAOs;


import Entities.Employee;
import Entities.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    @Override
    public void addTicket(Ticket t) {
        //Create the Config object
        Configuration cfg = new Configuration();

        //Read the config file and load it into the Config object
        cfg.configure("hibernate.cfg.xml");

        //Create the Session Factory
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        //Use the factory to create a Session
        Session session = sessionFactory.openSession();

        //Start the transaction
        Transaction tr = session.beginTransaction();

        //Save the Ticket object
        session.save(t);

        //Commit the transaction
        tr.commit();

        //Close the connection
        session.close();
    }

    @Override
    public void approveTicket(int t_id) {
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

        //Get the ticket object, modify it
        Ticket ticket = getTicketByID(t_id);
        ticket.setPending(false);
        ticket.setResolution_status("Approved");
        ticket.setResolution_date(new Date(System.currentTimeMillis()));

        //Save the Ticket object
        session.update(ticket);


        //Commit the transaction
        t.commit();

        //Close the connection
        session.close();
    }

    @Override
    public void rejectTicket(int t_id) {
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

        //Get the ticket object, modify it
        Ticket ticket = getTicketByID(t_id);
        ticket.setPending(false);
        ticket.setResolution_status("Rejected");
        ticket.setResolution_date(new Date(System.currentTimeMillis()));

        //Save the Ticket object
        session.update(ticket);


        //Commit the transaction
        t.commit();

        //Close the connection
        session.close();
    }

    @Override
    public Ticket getTicketByID(int t_id) {
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

        String hql = "from Ticket t where t.t_id = :ticket_id";
        Query query = session.createQuery(hql);
        query.setParameter("ticket_id", t_id);
        List<Ticket> tickets = query.getResultList();

        //Close the connection
        session.close();

        if (tickets.isEmpty()){ //The results are empty, found no ticket
            return null;
        }
        else{  //Results not empty, found at least one ticket
            return tickets.get(0); //Get the first one
        }
    }

    @Override
    public List<Ticket> getAllTicketsByOwner(int empl_id) {
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

        String hql = "from Ticket t where t.empl.empl_id = :employee_id";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id", empl_id);
        List<Ticket> tickets = query.getResultList();

        //Close the connection
        session.close();

        if (tickets.isEmpty()){ //The results are empty, found no ticket
            return null;
        }
        else{  //Results not empty, found at least one ticket
            return tickets;
        }
    }

    @Override
    public List<Ticket> getAllPendingTicketsByOwner(int empl_id) {
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

        String hql = "from Ticket t where t.empl.empl_id = :employee_id and t.pending = true";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id", empl_id);
        List<Ticket> tickets = query.getResultList();

        //Close the connection
        session.close();

        if (tickets.isEmpty()){ //The results are empty, found no ticket
            return null;
        }
        else{  //Results not empty, found at least one ticket
            return tickets;
        }
    }

    @Override
    public List<Ticket> getAllResolvedTicketsByOwner(int empl_id) {
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

        String hql = "from Ticket t where t.empl.empl_id = :employee_id and t.pending = false";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id", empl_id);
        List<Ticket> tickets = query.getResultList();

        //Close the connection
        session.close();

        if (tickets.isEmpty()){ //The results are empty, found no ticket
            return null;
        }
        else{  //Results not empty, found at least one ticket
            return tickets;
        }
    }

    @Override
    public List<Ticket> getAllPendingTickets() {
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

        String hql = "from Ticket t where t.pending = true";
        Query query = session.createQuery(hql);
        List<Ticket> tickets = query.getResultList();

        //Close the connection
        session.close();

        if (tickets.isEmpty()){ //The results are empty, found no ticket
            return null;
        }
        else{  //Results not empty, found at least one ticket
            return tickets;
        }
    }
}
