package Servlets;

import DAOs.EmployeeDao;
import DAOs.EmployeeDaoFactory;
import DAOs.TicketDao;
import DAOs.TicketDaoFactory;
import Entities.Employee;
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

public class ViewPendingTicketsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to view the pending tickets of an employee...");


        //Get the DAO to find all tickets
        TicketDao t_dao = TicketDaoFactory.getTicketDao();
        //List<Ticket> specific_tickets = t_dao.getAllPendingTicketsByOwner(empl_id);
        List<Ticket> all_tickets = t_dao.getAllPendingTickets();

        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");
        pw.println("<body class='text-center bg-dark'>");
//        pw.println("<h1 class='text-light'> Here are the tickets! </h1>");
//        pw.println("<table class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
//        pw.println("<thead>");
//        pw.println("<tr>");
//        pw.println("<td scope='col'>Ticket ID</td>");
//        pw.println("<td scope='col'>Value</td>");
//        pw.println("<td scope='col'>Approve</td>");
//        pw.println("<td scope='col'>Reject</td>");
//        pw.println("</tr>");
//        pw.println("</thead>");
//        pw.println("<tbody>");
//        for( Ticket t : specific_tickets){
//            pw.println("<tr>");
//            pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
//            pw.println("<td> $" + t.getAmount() + "</td>");
//            pw.println("<td> <form action='apprtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='submit' name='t_id' value='Approve'</td></form>");
//            pw.println("<td> <form action='rejtix' method='get'> <input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='submit' name='t_id' value='Reject'</td></form>");
//            pw.println("</tr>");
//        }
//        pw.println("</tbody>");
//        pw.println("</table>");
        pw.println("<h1 class='text-light'> Here are the requests! </h1>");
        pw.println("<input type='text' class='w-25' id='search_bar' onkeyup='onKeyPress()' placeholder=\"Search for an Employee's name\"/>");
        pw.println("<table id='ticket_table' class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
        pw.println("<thead>");
        pw.println("<tr>");
        pw.println("<td scope='col'>ID</td>");
        pw.println("<td scope='col'>Value</td>");
        pw.println("<td scope='col'>Employee Name & ID</td>");
        pw.println("<td scope='col'>Employee Email</td>");
        pw.println("<td scope='col'>Approve</td>");
        pw.println("<td scope='col'>Reject</td>");
        pw.println("</tr>");
        pw.println("</thead>");
        pw.println("<tbody>");
        for( Ticket t : all_tickets){
            pw.println("<tr>");
            pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
            pw.println("<td> $" + t.getAmount() + "</td>");
            pw.println("<td> " + t.getEmpl().getUsername() + "<sub>#"  + t.getEmpl().getEmpl_id() + "</sub></td>");
            pw.println("<td> " + t.getEmpl().getEmail() + "</td>");
            pw.println("<td> <form action='apprtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='image' class='' src='"+ req.getContextPath() + "/img/check-square-fill.svg' onclick='onButtonClick()' name='t_id' /></td></form>");
            pw.println("<td> <form action='rejtix' method='get'> <input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='image' class='' src='"+ req.getContextPath() + "/img/x-square-fill.svg' onclick='onButtonClick()' name='t_id' /></td></form>");
            pw.println("</tr>");
        }
        pw.println("</tbody>");
        pw.println("</table>");
        pw.println("<a href=\"viewresolvedtix\">View all resolved tickets</a>");
        pw.println("<a href=\"logout\">Log Out</a>");
        //Filter the table for username
        //Idea from W3 schools: source-https://www.w3schools.com/howto/howto_js_filter_table.asp
        pw.println("<script>");
        pw.println("function onButtonClick(){" +
                "this.src='';" +
                "let spinner = document.createElement(span);" +
                "spinner.class = 'spinner-border text-info';" +
                "this.appendChild(spinner)" +
                "}");
        pw.println("function onKeyPress(){");
            pw.println("var my_table = document.getElementById('ticket_table');");
            pw.println("var my_search_bar = document.getElementById('search_bar');");
            pw.println("var search_text = my_search_bar.value.toUpperCase()"); //Ignore case sensitivity
            pw.println("var rows = my_table.getElementsByTagName('tr');"); //Get all of the rows in the table
            pw.println("for(var i = 1; i < rows.length; i++){");
                pw.println("let cell = rows[i].getElementsByTagName('td')[2];"); //Get cell with the employee's name
                pw.println("if(cell){");
                    pw.println("let name_text = cell.textContent || cell.innerText"); //Guard operator
                    pw.println("if(name_text.toUpperCase().indexOf(search_text) != -1){"); //If the text is in that name somewhere
                        pw.println("rows[i].style.display = '';}");
                    pw.println("else{");
                        pw.println("rows[i].style.display = 'none';}");
                pw.println("}");
            pw.println("}");
        pw.println("}");
        pw.println("</script>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
