package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.Dao;
import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.dao.UserDao;
import edu.calpoly.csc365.example1.dao.ReservationsDaoImpl;
import edu.calpoly.csc365.example1.entity.Customer;
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
import java.util.Set;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private DaoManager dm = null;
    private AuthenticationService authenticationService = null;
    private Dao<Reservations> reservationDao;
    private Dao<User> userDao;
    private Dao<Customer> customerDao;

    public HomeServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        authenticationService = new AuthenticationService(dm.getUserDao());
        reservationDao = dm.getReservationsDao();
        userDao = dm.getUserDao();
        customerDao = dm.getCustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie loginCookie = AuthenticationService.getLoginCookie(request);
        if (loginCookie == null) {
            response.sendRedirect("login");
        } else {
            response.addCookie(loginCookie);
            String name = loginCookie.getValue();
            User user = userDao.getByName(name);
            Integer id = user.getCid();
            Customer c = customerDao.getById(id);
            Set<Reservations> reservations = reservationDao.getAllById(id);
            request.setAttribute("reservations", reservations);
            request.setAttribute("message", "Hello " + c.getFirstName() + " " + c.getLastName());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }
}