package Servlets;

import DAOs.EmployeeDao;
import DAOs.EmployeeDaoFactory;
import DAOs.ManagerDao;
import DAOs.ManagerDaoFactory;
import Entities.Employee;
import Entities.Manager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginManagerServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ManagerDao man_dao = ManagerDaoFactory.getManagerDao();


        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();

        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");

        Manager m = man_dao.getManagerByUsername(uname);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html"); //Get ready to send the user back if they fail


        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");

        if(m == null){ //Username is wrong.
            pw.println("<h1 class='text-light  text-center'> Login Failed. </h1>");

        }
        else{ //Username is right!
            if(m.getPassword().equals(pass)){ //Password is right!
                HttpSession session = req.getSession(true); //Create a new session
                session.setAttribute("uname",uname);         //Set the name
                session.setAttribute("email", m.getEmail()); //Set the email
                session.setAttribute("man_id", m.getMan_id()); //Set the id
                requestDispatcher = req.getRequestDispatcher("manager_choice.html"); //They succeeded! Change the dispatcher!
                pw.println("<h1 class='text-center'> Welcome, " + m.getUsername() + "! </h1>");
            }
            else{ //Password is wrong.
                pw.println("<h1 class='text-light  text-center'> Login Failed. </h1>");
            }
        }
        requestDispatcher.include(req, resp);
    }
}
