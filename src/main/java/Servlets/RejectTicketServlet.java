package Servlets;

import DAOs.TicketDao;
import DAOs.TicketDaoFactory;
import Entities.Ticket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RejectTicketServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to reject a ticket...");



        //Get the ticket's ID from the page
        int ticket_id = Integer.parseInt(req.getParameter("t_id"));
        //Get the DAO to find the ticket
        TicketDao t_dao = TicketDaoFactory.getTicketDao();
        Ticket ticket = t_dao.getTicketByID(ticket_id);



        if(ticket != null){ //If the ticket is found
            t_dao.rejectTicket(ticket_id);
            pw.println("<h1 class='text-light text-center'> Ticket rejected! </h1>");
        }
        else{
            pw.println("<h1 class='text-light text-center'> Ticket not found! </h1>");
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("viewtix");
        requestDispatcher.include(req, resp);
    }
}
