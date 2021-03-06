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
        HttpSession session = req.getSession(false);
        if(session == null){
            System.out.println("Session invalid.");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html");
            requestDispatcher.forward(req, resp);
        }

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
        if(all_tickets == null || all_tickets.isEmpty()){
            pw.println("<h1 class='text-light'> No pending tickets found.</h1>");
        }
        else{
            pw.println("<h1 class='text-light m-auto'> Pending Tickets </h1>");
            pw.println("<input type='text' class='w-25' id='search_bar' onkeyup='onKeyPress()' placeholder=\"Search for an Employee's name\"/>");
            pw.println("<table id='ticket_table' class='table w-75 table-bordered table-sm table-striped table-hover table-light m-auto'>");
            pw.println("<thead>");
            pw.println("<tr>");
            pw.println("<td scope='col'>ID</td>");
            pw.println("<td scope='col'>Value</td>");
            pw.println("<td scope='col'>Employee Name & ID</td>");
            pw.println("<td scope='col'>Employee Email</td>");
            pw.println("<td scope='col'>Description</td>");
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
                    pw.println("<td> " + t.getEmpl().getUsername() + "<sub>#"  + t.getEmpl().getEmpl_id() + "</sub></td>");
                    pw.println("<td> " + t.getEmpl().getEmail() + "</td>");
                    pw.println("<td> " + desc + "</td>");
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
        }


        //Filter the table for username
        //Idea from W3 schools: source-https://www.w3schools.com/howto/howto_js_filter_table.asp
        pw.println("<script>");
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
        pw.println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>");
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
                "                   <li class=\"nav-item\">\n" +
                "                    <a href=\"manager_choice.html\" class=\"nav-link\">Ticket View Home</a>\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" fill=\"currentColor\" class=\"bi bi-box-arrow-in-left\" viewBox=\"0 0 16 16\">\n" +
                "                        <path fill-rule=\"evenodd\" d=\"M10 3.5a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 1 1 0v2A1.5 1.5 0 0 1 9.5 14h-8A1.5 1.5 0 0 1 0 12.5v-9A1.5 1.5 0 0 1 1.5 2h8A1.5 1.5 0 0 1 11 3.5v2a.5.5 0 0 1-1 0v-2z\"/>\n" +
                "                        <path fill-rule=\"evenodd\" d=\"M4.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H14.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z\"/>\n" +
                "                      </svg>\n" +
                "                </li>" +
                "                   <li class=\"nav-item\">\n" +
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
