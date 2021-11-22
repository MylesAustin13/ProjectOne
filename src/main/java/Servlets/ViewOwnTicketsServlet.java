package Servlets;

import DAOs.TicketDao;
import DAOs.TicketDaoFactory;
import Entities.Ticket;

import javax.servlet.RequestDispatcher;
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

        System.out.println("Going to view the resolved tickets of employees...");

        HttpSession session = req.getSession(false);
        if(session == null){
            System.out.println("Session invalid.");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html");
            requestDispatcher.forward(req, resp);
        }
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

        if(pending_tickets == null || pending_tickets.isEmpty()){
            pw.println("<h1 class='text-light m-auto'> You don't have any pending tickets. </h1>");
        }
        else{
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
        }
        pw.println("<hr />");
        if(resolved_tickets == null || resolved_tickets.isEmpty()){
            pw.println("<h1 class='text-light m-auto'> You don't have any resolved tickets. </h1>");
        }
        else {
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
        }
        pw.println("<nav class=\"navbar fixed-bottom navbar-expand-lg navbar-light bg-light\">\n" +
                "        <a class=\"navbar-brand\" href=\"#\">Revature</a>\n" +
                "\n" +
                "        <div class=\"collapse navbar-collapse text-center\" id=\"navbarNav\">\n" +
                "            <ul class=\"navbar-nav\">\n" +
                "                <li class=\"nav-item\">\n" +
                "                    <a class=\"nav-link\" href=\"#\">About</a>\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" fill=\"currentColor\"\n" +
                "                        class=\"bi bi-book\" viewBox=\"0 0 16 16\">\n" +
                "                        <path\n" +
                "                            d=\"M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z\" />\n" +
                "                    </svg>\n" +
                "                </li>\n" +
                "                <li class=\"nav-item\">\n" +
                "                    <a class=\"nav-link disabled\" href=\"#\">Contact Us</a>\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" fill=\"currentColor\"\n" +
                "                        class=\"bi bi-telephone\" viewBox=\"0 0 16 16\">\n" +
                "                        <path\n" +
                "                            d=\"M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z\" />\n" +
                "                    </svg>\n" +
                "                </li>\n" +
                "               <li class=\"nav-item\">\n" +
                "                    <a href=\"ticket_creation.html\" class=\"nav-link\">Ticket Creation</a>\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" fill=\"currentColor\" class=\"bi bi-box-arrow-in-left\" viewBox=\"0 0 16 16\">\n" +
                "                        <path fill-rule=\"evenodd\" d=\"M10 3.5a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 1 1 0v2A1.5 1.5 0 0 1 9.5 14h-8A1.5 1.5 0 0 1 0 12.5v-9A1.5 1.5 0 0 1 1.5 2h8A1.5 1.5 0 0 1 11 3.5v2a.5.5 0 0 1-1 0v-2z\"/>\n" +
                "                        <path fill-rule=\"evenodd\" d=\"M4.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H14.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z\"/>\n" +
                "                      </svg>\n" +
                "                </li> " +
                "               <li class=\"nav-item\">\n" +
                "\n" +
                "\n" +
                "                    <a class='nav-link' href=\"logout\">Log Out</a>\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" fill=\"currentColor\"\n" +
                "                        class=\"bi bi-door-open\" viewBox=\"0 0 16 16\">\n" +
                "                        <path d=\"M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z\" />\n" +
                "                        <path\n" +
                "                            d=\"M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z\" />\n" +
                "                    </svg>\n" +
                "\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </nav>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
