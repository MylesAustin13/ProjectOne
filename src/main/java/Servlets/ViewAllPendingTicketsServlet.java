package Servlets;

import DAOs.TicketDao;
import DAOs.TicketDaoFactory;
import Entities.Ticket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewAllPendingTicketsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to view the pending tickets of an employee...");


        //Get the DAO to find all tickets
        TicketDao t_dao = TicketDaoFactory.getTicketDao();
        List<Ticket> tickets = t_dao.getAllPendingTickets();

        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");
        pw.println("<body class='text-center bg-dark'>");
        pw.println("<h1 class='text-light'> Here are the tickets! </h1>");
        pw.println("<table class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
        pw.println("<thead>");
        pw.println("<tr>");
        pw.println("<td scope='col'>ID</td>");
        pw.println("<td scope='col'>Value</td>");
        pw.println("<td scope='col'>Employee ID</td>");
        pw.println("<td scope='col'>Employee Name</td>");
        pw.println("<td scope='col'>Employee Email</td>");
        pw.println("<td scope='col'>Approve</td>");
        pw.println("<td scope='col'>Reject</td>");
        pw.println("</tr>");
        pw.println("</thead>");
        pw.println("<tbody>");
        for( Ticket t : tickets){
            pw.println("<tr>");
            pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
            pw.println("<td> $" + t.getAmount() + "</td>");
            pw.println("<td> " + t.getEmpl().getEmpl_id() + "</td>");
            pw.println("<td> " + t.getEmpl().getUsername() + "</td>");
            pw.println("<td> " + t.getEmpl().getEmail() + "</td>");
            pw.println("<td> <form action='apprtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='submit' name='t_id' value='Approve'</td></form>");
            pw.println("<td> <form action='rejtix' method='get'> <input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='submit' name='t_id' value='Reject'</td></form>");
            pw.println("</tr>");
        }
        pw.println("</tbody>");
        pw.println("</table>");
        pw.println("<div class='container border border-3 text-center p-2 bg-light w-75'>");
        pw.println("    <a class='link-dark' href=\"employee_creation.html\">Add an Employee</a>  <br />\n" +
                "    <a class='link-dark' href=\"employee_update.html\"> Update an Employee</a> <br />\n" +
                "    <a class='link-dark' href=\"employee_deletion.html\">Delete an Employee</a> <br />");
        pw.println("<div>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
