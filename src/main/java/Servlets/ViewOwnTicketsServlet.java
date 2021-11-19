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

public class ViewOwnTicketsServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to view the resolved tickets of an employee...");

        HttpSession session = req.getSession(false);
        int empl_id = (Integer) session.getAttribute("empl_id");

        //Get the DAO to find all tickets
        TicketDao t_dao = TicketDaoFactory.getTicketDao();
        List<Ticket> pending_tickets = t_dao.getAllPendingTicketsByOwner(empl_id);
        List<Ticket> resolved_tickets = t_dao.getAllResolvedTicketsByOwner(empl_id);


        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");
        pw.println("<body class='text-center bg-dark'>");
        pw.println("<h1 class='text-light'> Here are your pending tickets! </h1>");
        pw.println("<table class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
        pw.println("<thead>");
        pw.println("<tr>");
        pw.println("<td scope='col'>Ticket ID</td>");
        pw.println("<td scope='col'>Value</td>");
        pw.println("<td scope='col'>Description</td>");
        pw.println("</tr>");
        pw.println("</thead>");
        pw.println("<tbody>");
        if(pending_tickets != null){
            for( Ticket t : pending_tickets){
                String desc = t.getDescription();
                if(desc == null || desc.isEmpty()){
                    desc = "N/A";
                }
                pw.println("<tr>");
                pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
                pw.println("<td> $" + t.getAmount() + "</td>");
                pw.println("<td> " + desc + "</td>");
                pw.println("</tr>");
            }
        }
        pw.println("</tbody>");
        pw.println("</table>");
        pw.println("<h1 class='text-light'> Here are your resolved tickets! </h1>");
        pw.println("<table class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
        pw.println("<thead>");
        pw.println("<tr>");
        pw.println("<td scope='col'>ID</td>");
        pw.println("<td scope='col'>Value</td>");
        pw.println("<td scope='col'>Description</td>");
        pw.println("<td scope='col'>Status</td>");
        pw.println("<td scope='col'>Resolution Date</td>");
        pw.println("<td scope='col'>Comments from Manager</td>");
        pw.println("</tr>");
        pw.println("</thead>");
        pw.println("<tbody>");
        if(resolved_tickets != null){
            for( Ticket t : resolved_tickets){
                String desc = t.getDescription();
                if(desc == null || desc.isEmpty()){
                    desc = "N/A";
                }
                String reason = t.getResolve_message();
                if(reason == null || reason.isEmpty()){
                    reason = "N/A";
                }
                pw.println("<tr>");
                pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
                pw.println("<td> $" + t.getAmount() + "</td>");
                pw.println("<td> " + desc + "</td>");
                pw.println("<td> " + t.getResolution_status() + "</td>");
                pw.println("<td> " + t.getResolution_date() + "</td>");
                pw.println("<td> " + reason + "</td>");
                pw.println("</tr>");
            }
        }

        pw.println("</tbody>");
        pw.println("</table>");
        pw.println("<a href=\"logout\">Log Out</a>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
