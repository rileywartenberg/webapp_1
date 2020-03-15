package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.*;
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

@WebServlet(name = "ReservationsAvailabilityServlet", urlPatterns = "/availability_reservations")
public class ReservationsAvailabilityServlet extends HttpServlet {
    private DaoManager dm; //= null;
    private Dao<Availability> availabilityDao;// = null;
    private Dao<User> userDao;
    private Dao<Rooms> roomsDao;
    private Date day;


    public ReservationsAvailabilityServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        availabilityDao = dm.getAvailabilityDao();
        userDao = dm.getUserDao();
        roomsDao = dm.getRoomsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("room_availability.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("day"));
        Date date = Date.valueOf(request.getParameter("day"));
        Set<Availability> availabilities = availabilityDao.getByDate(date);
        System.out.println(availabilities);
        request.setAttribute("availabilities", availabilities);
        request.setAttribute(("message"), "Here are the available rooms for the day of" + date);
        request.getRequestDispatcher("display_availabilities.jsp").forward(request, response);
    }
}

