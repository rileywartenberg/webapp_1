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
import java.util.ArrayList;
import java.util.Iterator;
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
        Double jan = 0.0, feb = 0.0, mar = 0.0, apr = 0.0, may = 0.0, jun = 0.0, jul = 0.0, aug = 0.0, sept = 0.0, oct = 0.0, nov = 0.0, dec = 0.0, total = 0.0;
        String totalName = "Total";
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        if (loginCookie == null) {
            response.sendRedirect("admin");
        } else {
            response.addCookie(loginCookie);
            Set<Report> reports = ((ReportDaoImpl)reportDao).display();
            for(int i = 0; i < reports.size(); i++)
            {
                jan += ((Report)reports.toArray()[i]).getJanuary();
                feb += ((Report)reports.toArray()[i]).getFebruary();
                mar += ((Report)reports.toArray()[i]).getMarch();
                apr += ((Report)reports.toArray()[i]).getApril();
                may += ((Report)reports.toArray()[i]).getMay();
                jun += ((Report)reports.toArray()[i]).getJune();
                jul += ((Report)reports.toArray()[i]).getJuly();
                aug += ((Report)reports.toArray()[i]).getAugust();
                sept += ((Report)reports.toArray()[i]).getSeptember();
                oct += ((Report)reports.toArray()[i]).getOctober();
                nov += ((Report)reports.toArray()[i]).getNovember();
                dec += ((Report)reports.toArray()[i]).getDecember();
                total += ((Report)reports.toArray()[i]).getTotal();
            }
            Report newReport = new Report(totalName, jan, feb, mar, apr, may, jun, jul, aug, sept, oct, nov, dec, total);
            ArrayList<Report> reportList = new ArrayList<Report>();
            reportList.addAll(reports);
            reportList.add(newReport);
            System.out.println("username: " + loginCookie.getValue());
            request.setAttribute("reports", reportList);
            request.setAttribute("message", "Hello " + loginCookie.getValue());
            request.getRequestDispatcher("report.jsp").forward(request, response);
        }
    }
}