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
import java.io.IOException;
import java.io.PrintWriter;

public class AddManagerServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Going to add an employee...");


        //Get the parameters from the request
        String man_uname = req.getParameter("uname");
        String man_pass = req.getParameter("pass");
        String man_email = req.getParameter("email");

        //Create an employee
        Manager man = new Manager(man_uname, man_pass, man_email);

        //Get the DAO
        ManagerDao dao = ManagerDaoFactory.getManagerDao();
        dao.addManager(man);


        pw.println("<head>");
        pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        pw.println("</head>");
        pw.println("<h1 class='text-light  text-center'> Employee Added! </h1>");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html");
        requestDispatcher.include(req, resp);
    }
}
