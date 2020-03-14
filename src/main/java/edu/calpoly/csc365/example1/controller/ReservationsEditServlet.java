package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Rooms;
import edu.calpoly.csc365.example1.entity.User;
import edu.calpoly.csc365.example1.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "ReservationsEditServlet", urlPatterns = "/edit_reservations")
public class ReservationsEditServlet extends HttpServlet {
    private DaoManager dm;
    private Dao<Reservations> reservationsDao;
    private Dao<User> userDao;
    private Dao<Rooms> roomsDao;

    public ReservationsEditServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
        userDao = dm.getUserDao();
        roomsDao = dm.getRoomsDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        response.addCookie(loginCookie);
        String name = loginCookie.getValue();
        User user = userDao.getByName(name);
        Integer cid = user.getCid();

        Integer id = Integer.parseInt(request.getParameter("id"));
        String room = request.getParameter("room");
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        Integer ccnum = Integer.parseInt(request.getParameter("ccnum"));
        Integer adults = Integer.parseInt(request.getParameter("adults"));
        Integer kids = Integer.parseInt(request.getParameter("kids"));
        Rooms rooms = ((RoomsDaoImpl)roomsDao).getByRoomName(room);
        Double rate = rooms.getBasePrice();

        System.out.println(checkin);
        System.out.println(checkout);

        Reservations reservations = reservationsDao.getById(id);

        if (reservations.getCid() != cid) {
            request.setAttribute("message", "Unauthorized command");
            request.getRequestDispatcher("reservations_edit.jsp").forward(request, response);
            return;
        }

        reservations.setId(id);
        reservations.setRoom(room);
        reservations.setCheckin(checkin);
        reservations.setCheckout(checkout);
        reservations.setRate(rate);
        reservations.setAdults(adults);
        reservations.setKids(kids);
        reservations.setCcnum(ccnum);

        DaoCommand daoCommand = new ReservationsDaoCommandImpl(reservations);
        message = dm.transactionUpdate(daoCommand);

        if(!message.equals("runError") && !message.equals("0") && !message.equals("1"))
        {
            request.setAttribute("message", message);
            request.getRequestDispatcher("reservations_edit.jsp").forward(request, response);
        }
        else {
            Set<Reservations> reservationsSet = new HashSet<>();
            reservationsSet.add(reservations);
            request.setAttribute("reservations", reservationsSet);
            request.setAttribute("message", "New Reservation");
            request.getRequestDispatcher("display_reservation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_edit.jsp").forward(request, response);
    }

}