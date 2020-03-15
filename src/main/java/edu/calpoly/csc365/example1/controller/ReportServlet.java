package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.dao.Dao;
import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.dao.ReportDaoImpl;
import edu.calpoly.csc365.example1.entity.Report;
import edu.calpoly.csc365.example1.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "ReportServlet", urlPatterns = "/report")
public class ReportServlet extends HttpServlet {

    private DaoManager dm;
    private Dao<Report> reportDao;

    public ReportServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reportDao = dm.getReportDao();
    }

 /*   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }*/


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        if (loginCookie == null) {
            response.sendRedirect("admin");
        } else {
            response.addCookie(loginCookie);
            Set<Report> reports = ((ReportDaoImpl)reportDao).display();
            System.out.println("username: " + loginCookie.getValue());
            request.setAttribute("reports", reports);
            request.setAttribute("message", "Hello " + loginCookie.getValue());
            request.getRequestDispatcher("report.jsp").forward(request, response);
        }
    }
}