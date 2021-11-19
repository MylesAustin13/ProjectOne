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
        pw.println("<td scope='col'>Description</td>");
        pw.println("<td scope='col'>Employee Name & ID</td>");
        pw.println("<td scope='col'>Employee Email</td>");
        pw.println("<td scope='col'>Approve</td>");
        pw.println("<td scope='col'>Reject</td>");
        pw.println("</tr>");
        pw.println("</thead>");
        pw.println("<tbody>");
        if(all_tickets != null){
            for( Ticket t : all_tickets){
                String desc = t.getDescription();
                if(desc == null || desc.isEmpty()){
                    desc = "N/A";
                }
                pw.println("<tr>");
                pw.println("<td scope='row'>" + t.getT_id() +   "</td>");
                pw.println("<td> $" + t.getAmount() + "</td>");
                pw.println("<td> " + desc + "</td>");
                pw.println("<td> " + t.getEmpl().getUsername() + "<sub>#"  + t.getEmpl().getEmpl_id() + "</sub></td>");
                pw.println("<td> " + t.getEmpl().getEmail() + "</td>");
                pw.println("<td><div class=\"dropdown\">\n" + //Drop down button for approval button
                        "  <button class=\"btn btn-secondary btn-success\" type='button' id='dropdownMenuButton" + t.getT_id() + "' data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
                        " <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" fill=\"currentColor\" class=\"bi bi-check2\" viewBox=\"0 0 16 16\">\n" +
                        "  <path d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>\n" +
                        "</svg>\n" +
                        "  </button>\n" +
                        "  <div class='dropdown-menu text-center' aria-labelledby='dropdownMenuButton" + t.getT_id() + "'>\n" +
                        "    <form action='apprtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/>\n" +
                        "    <label>Approve this request?<label/>\n" +
                        "    <input class='text-center' type='text' name='t_reason' placeholder='Additional Comments'/>\n" +
                        "    <input type='submit' class='btn-success' value='APPROVE!' />\n" +
                        "  </form></div>\n" +
                        "</div></td>");
                pw.println("<td><div class=\"dropdown\">\n" + //Drop down button for reject button
                        "  <button class=\"btn btn-secondary btn-danger\" type='button' id='dropdownMenuButton" + t.getT_id() + "' data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
                        " <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" fill=\"currentColor\" class=\"bi bi-x-lg\" viewBox=\"0 0 16 16\">\n" +
                        "  <path fill-rule=\"evenodd\" d=\"M13.854 2.146a.5.5 0 0 1 0 .708l-11 11a.5.5 0 0 1-.708-.708l11-11a.5.5 0 0 1 .708 0Z\"/>\n" +
                        "  <path fill-rule=\"evenodd\" d=\"M2.146 2.146a.5.5 0 0 0 0 .708l11 11a.5.5 0 0 0 .708-.708l-11-11a.5.5 0 0 0-.708 0Z\"/>\n" +
                        "</svg>" +
                        "  </button>\n" +
                        "  <div class='dropdown-menu text-center' aria-labelledby='dropdownMenuButton" + t.getT_id() + "'>\n" +
                        "    <form action='rejtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/>\n" +
                        "    <label>Reject this request?<label/>\n" +
                        "    <input class='text-center' type='text' name='t_reason' placeholder='Additional Comments'/>\n" +
                        "    <input type='submit' class='btn-danger' value='REJECT!' />\n" +
                        "  </form></div>\n" +
                        "</div></td>");
                //pw.println("<td> <form action='apprtix' method='get'><input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='image' class='' src='"+ req.getContextPath() + "/img/check-square-fill.svg' onclick='onButtonClick()' name='t_id' /></td></form>");
                //pw.println("<td> <form action='rejtix' method='get'> <input type='hidden' name='t_id' value='" + t.getT_id() +"'/><input type='image' class='' src='"+ req.getContextPath() + "/img/x-square-fill.svg' onclick='onButtonClick()' name='t_id' /></td></form>");
                pw.println("</tr>");
            }
        }

        pw.println("</tbody>");
        pw.println("</table>");
        pw.println("<a href=\"viewresolvedtix\">View all resolved tickets</a>");
        pw.println("<a href=\"logout\">Log Out</a>");
        //Filter the table for username
        //Idea from W3 schools: source-https://www.w3schools.com/howto/howto_js_filter_table.asp
        pw.println("<script>");
        pw.println("function onKeyPress(){");
            pw.println("var my_table = document.getElementById('ticket_table');");
            pw.println("var my_search_bar = document.getElementById('search_bar');");
            pw.println("var search_text = my_search_bar.value.toUpperCase()"); //Ignore case sensitivity
            pw.println("var rows = my_table.getElementsByTagName('tr');"); //Get all of the rows in the table
            pw.println("for(var i = 1; i < rows.length; i++){");
                pw.println("let cell = rows[i].getElementsByTagName('td')[3];"); //Get cell with the employee's name
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
        pw.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
