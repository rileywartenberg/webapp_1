package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.service.AuthenticationAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private DaoManager dm = null;
    private AuthenticationAdmin authenticationAdmin = null;

    public AdminServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        authenticationAdmin = new AuthenticationAdmin(dm.getAdminDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        if (authenticationAdmin.authenticate(name, pass)) {
            Cookie loginCookie = AuthenticationAdmin.createLoginCookie(name);
            response.addCookie(loginCookie);
            response.sendRedirect("report");
        } else {
            response.sendRedirect("admin");
        }
    }

}