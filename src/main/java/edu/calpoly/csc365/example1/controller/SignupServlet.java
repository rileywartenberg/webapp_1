package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.Dao;
import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.dao.UserDao;
import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.User;
import edu.calpoly.csc365.example1.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignupServlet", urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    private DaoManager dm = null;
    private Dao<Customer> customerDao = null;
    private Dao<User> userDao = null;

    public SignupServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        customerDao = dm.getCustomerDao();
        userDao = dm.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String ssn = request.getParameter("ssn");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        Customer customer = new Customer(id, ssn, lastName, firstName, address, phoneNumber);
        id = this.customerDao.insert(customer);
        User user = new User();
        user.setCid(id);
        user.setName(name);
        user.setPass(pass);
        this.userDao.insert(user);
        response.sendRedirect("edit_customer?id=" + id.toString());
    }

}