package Servlets;


import DAOs.EmployeeDao;
import DAOs.EmployeeDaoFactory;
import DAOs.EmployeeDaoImpl;
import Entities.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginEmployeeServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        EmployeeDao empl_dao = EmployeeDaoFactory.getEmployeeDao();


        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();

        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");

        Employee e = empl_dao.getEmployeeByUsername(uname);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html"); //Get ready to send the user back if they fail


        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");

        if(e == null){ //Username is wrong.
            pw.println("<h1 class='text-light  text-center'> Login Failed. </h1>");

            requestDispatcher.include(req, resp);
        }
        else{ //Username is right!
            if(e.getPassword().equals(pass)){ //Password is right!
                HttpSession session = req.getSession(); //Create a new session
                session.setAttribute("uname",uname);         //Set the name
                session.setAttribute("email", e.getEmail()); //Set the email

                requestDispatcher = req.getRequestDispatcher("ticket_creation.html"); //They succeeded! Change the dispatcher!
                pw.println("<h1 class='text-light  text-center'> Welcome, " + e.getUsername() + "! </h1>");
                requestDispatcher.include(req, resp);
            }
            else{ //Password is wrong.
                pw.println("<h1 class='text-light  text-center'> Login Failed. </h1>");
                requestDispatcher.include(req, resp);
            }
        }





    }
}
