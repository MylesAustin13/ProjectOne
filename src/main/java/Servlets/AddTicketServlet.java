package Servlets;

import DAOs.EmployeeDao;
import DAOs.EmployeeDaoFactory;
import DAOs.TicketDao;
import DAOs.TicketDaoFactory;
import Entities.Employee;
import Entities.Ticket;

import javax.persistence.Id;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddTicketServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to add an employee...");


        //Get the parameters from the request
        HttpSession session = req.getSession(false);
        int empl_id = (Integer) session.getAttribute("empl_id");
        Double ticket_value = Double.parseDouble(req.getParameter("amount"));

        //Get DAO to find the logged in employee
        EmployeeDao e_dao = EmployeeDaoFactory.getEmployeeDao();
        //Create an employee
        Employee emp = e_dao.getEmployeeByID(empl_id);

        //Create ticket
        Ticket ticket = new Ticket(emp, ticket_value);

        //Get the DAO to add the ticket
        TicketDao t_dao = TicketDaoFactory.getTicketDao();
        t_dao.addTicket(ticket);


        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");
        pw.println("<h1 class='text-light  text-center'> Reimbursement Request Made! </h1>");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("ticket_creation.html");
        requestDispatcher.include(req, resp);
    }
}
