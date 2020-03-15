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

@WebServlet(name = "SearchAvailabilityServlet", urlPatterns = "/search_availability")
public class SearchAvailabilityServlet extends HttpServlet {
    private DaoManager dm; //= null;
    private Dao<Availability> availabilityDao;// = null;
    private Dao<User> userDao;
    private Dao<Rooms> roomsDao;
    private Date day;


    public SearchAvailabilityServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        availabilityDao = dm.getAvailabilityDao();
        userDao = dm.getUserDao();
        roomsDao = dm.getRoomsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("search_availability.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Date checkinDate = Date.valueOf(request.getParameter("checkinDate"));
        Date checkoutDate = Date.valueOf(request.getParameter("checkoutDate"));
        Double minRate = Double.parseDouble(request.getParameter("minRate"));
        Double maxRate = Double.parseDouble(request.getParameter("maxRate"));
        String bedType = request.getParameter(request.getParameter("bedType"));
        Integer beds = Integer.parseInt(request.getParameter("beds"));
        Integer maxOccupancy = Integer.parseInt(request.getParameter("maxOccupancy"));
        Set<Availability> availabilities = ((RoomAvailabilityDaoImpl)availabilityDao).getByEverything(checkinDate, checkoutDate, minRate, maxRate, bedType, beds, maxOccupancy);
        request.setAttribute("availabilities", availabilities);
        request.setAttribute(("message"), "Here are the available rooms:");
        request.getRequestDispatcher("display_availabilities.jsp").forward(request, response);
    }
}

