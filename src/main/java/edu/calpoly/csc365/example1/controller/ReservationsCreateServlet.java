package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.*;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@WebServlet(name = "ReservationsCreateServlet", urlPatterns = "/create_reservations")
public class ReservationsCreateServlet extends HttpServlet {
    private DaoManager dm; //= null;
    private Dao<Reservations> reservationsDao;// = null;
    private Dao<User> userDao;
    private Dao<Rooms> roomsDao;

    public ReservationsCreateServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
        userDao = dm.getUserDao();
        roomsDao = dm.getRoomsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        response.addCookie(loginCookie);
        String name = loginCookie.getValue();
        User user = userDao.getByName(name);
        Integer cid = user.getCid();
      //  Integer id = Integer.parseInt(request.getParameter("id"));
      //  Integer cid = Integer.parseInt(request.getParameter("cid"));
        String room = request.getParameter("room");
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        //Double rate = Double.parseDouble(request.getParameter("rate"));
        Integer adults = Integer.parseInt(request.getParameter("adults"));
        Integer kids = Integer.parseInt(request.getParameter("kids"));
        Integer ccnum = Integer.parseInt(request.getParameter("ccnum"));

        Rooms rooms = ((RoomsDaoImpl)roomsDao).getByRoomName(room);
        Double rate = rooms.getBasePrice();
        //Transaction
        Reservations reservations = new Reservations();
        reservations.setCid(cid);
       // reservations.setId(id);
        reservations.setRoom(room);
        reservations.setCheckin(checkin);
        reservations.setCheckout(checkout);
        reservations.setRate(rate);
        reservations.setAdults(adults);
        reservations.setKids(kids);
        reservations.setCcnum(ccnum);

        DaoCommand daoCommand = new ReservationsDaoCommandImpl(reservations);
        Object result = dm.transaction(daoCommand);
        //System.out.println(result);
        //Object result = daoCommand.execute(this.dm);
        if (result != null) {
            reservations = (Reservations) result;
        }
       // PrintWriter out = response.getWriter();
        //out.println(reservations);
        //out.close();
        Set<Reservations> reservationsSet = new HashSet<>();
        reservationsSet.add(reservations);
        request.setAttribute("reservations", reservationsSet);
        request.setAttribute("message", "New Reservation");
        request.getRequestDispatcher("display_reservation.jsp").forward(request, response);
    //    response.sendRedirect("home");
        
        
    //    id = this.reservationsDao.insert(reservations);
      //  response.sendRedirect("edit_reservations?id=" + id.toString());
    }
}