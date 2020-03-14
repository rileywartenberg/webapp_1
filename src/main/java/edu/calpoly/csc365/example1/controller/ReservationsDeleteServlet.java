package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.Reservations;
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

@WebServlet(name = "ReservationsDeleteServlet", urlPatterns = "/delete_reservations")
public class ReservationsDeleteServlet extends HttpServlet {
    private DaoManager dm;
    private Dao<Reservations> reservationsDao;
    private Dao<User> userDao;

    public ReservationsDeleteServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
        userDao = dm.getUserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        response.addCookie(loginCookie);
        String name = loginCookie.getValue();
        User user = userDao.getByName(name);
        Integer cid = user.getCid();

        Reservations reservations = reservationsDao.getById(id);

        if (reservations.getCid() != cid) {
            request.setAttribute("message", "Unauthorized command");
            request.getRequestDispatcher("reservations_delete.jsp").forward(request, response);
            return;
        }

        DaoCommand daoCommand = new ReservationsDaoCommandImpl(reservations);
        Reservations reservation = reservationsDao.getById(id);
        String message = dm.transactionDelete(daoCommand);

        if(!message.equals("runError") && !message.equals("0") && !message.equals("1"))
        {
            request.setAttribute("message", message);
            request.getRequestDispatcher("reservations_delete.jsp").forward(request, response);
        }
        else {
            Set<Reservations> reservationsSet = new HashSet<>();
            reservationsSet.add(reservation);
            request.setAttribute("reservations", reservationsSet);
            request.setAttribute("message", "New Reservation");
            request.getRequestDispatcher("display_reservation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_delete.jsp").forward(request, response);
    }

}