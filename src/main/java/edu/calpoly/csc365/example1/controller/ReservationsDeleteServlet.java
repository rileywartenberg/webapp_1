package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.Reservations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "ReservationsDeleteServlet", urlPatterns = "/delete_reservations")
public class ReservationsDeleteServlet extends HttpServlet {
    private DaoManager dm;
    private Dao<Reservations> reservationsDao;

    public ReservationsDeleteServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
/*        Integer cid = Integer.parseInt(request.getParameter("cid"));
        String room = request.getParameter("room");
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        System.out.println(checkin);

        Date checkout = Date.valueOf(request.getParameter("checkout"));
        System.out.println(checkout);

        Double rate = Double.parseDouble(request.getParameter("rate"));
        Integer adults = Integer.parseInt(request.getParameter("adults"));
        Integer kids = Integer.parseInt(request.getParameter("kids"));*/

        Reservations reservations = new Reservations();
      //  reservations.setCid(cid);
        reservations.setId(id);
   /*     reservations.setRoom(room);
        reservations.setCheckin(checkin);
        reservations.setCheckout(checkout);
        reservations.setRate(rate);
        reservations.setAdults(adults);
        reservations.setKids(kids);*/

        DaoCommand daoCommand = new ReservationsDaoCommandImpl(reservations);
        Reservations reservation = reservationsDao.getById(id);
        Object result = dm.transactionDelete(daoCommand);
        //System.out.println(result);
        //Object result = daoCommand.execute(this.dm);
        if (result != null) {
            reservations = (Reservations) result;
        }


        // PrintWriter out = response.getWriter();
        //out.println(reservations);
        //out.close();
        Set<Reservations> reservationsSet = new HashSet<>();
        reservationsSet.add(reservation);
        request.setAttribute("reservations", reservationsSet);
        request.setAttribute("message", "New Reservation");
        request.getRequestDispatcher("display_reservation.jsp").forward(request, response);
        //this.reservationsDao.delete(reservations);
        //response.sendRedirect("home");

//        request.setAttribute("reservations", reservations);
  //      request.getRequestDispatcher("reservations_delete.jsp").forward(request, response);
        //response.sendRedirect("home");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_delete.jsp").forward(request, response);
    }

}