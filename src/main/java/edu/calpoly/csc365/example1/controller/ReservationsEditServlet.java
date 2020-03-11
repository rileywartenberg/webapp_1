package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.dao.Dao;
import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.entity.Reservations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "ReservationsEditServlet", urlPatterns = "/edit_reservations")
public class ReservationsEditServlet extends HttpServlet {
    private DaoManager dm;
    private Dao<Reservations> reservationsDao;

    public ReservationsEditServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        String room = request.getParameter("room");
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        System.out.println(checkin);

        Date checkout = Date.valueOf(request.getParameter("checkout"));
        System.out.println(checkout);

        Double rate = Double.parseDouble(request.getParameter("rate"));
        Integer adults = Integer.parseInt(request.getParameter("adults"));
        Integer kids = Integer.parseInt(request.getParameter("kids"));

        Reservations reservations = new Reservations();
        reservations.setCid(cid);
        reservations.setId(id);
        reservations.setRoom(room);
        reservations.setCheckin(checkin);
        reservations.setCheckout(checkout);
        reservations.setRate(rate);
        reservations.setAdults(adults);
        reservations.setKids(kids);


        this.reservationsDao.update(reservations);
        response.sendRedirect("home");

//        request.setAttribute("reservations", reservations);
        //      request.getRequestDispatcher("reservations_edit.jsp").forward(request, response);
        //response.sendRedirect("home");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_edit.jsp").forward(request, response);
    }

}