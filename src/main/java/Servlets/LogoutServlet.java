package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        System.out.println("Logging out...");
        //Get the session
        HttpSession session = req.getSession();
        session.invalidate();

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.html"); //Get ready to send the user back to login screen
        pw.println("<h1 class='text-light  text-center'> Logged out. </h1>");
        requestDispatcher.include(req, resp);

    }
}
