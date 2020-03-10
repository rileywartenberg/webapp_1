package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.Dao;
import edu.calpoly.csc365.example1.dao.DaoManager;
import edu.calpoly.csc365.example1.dao.DaoManagerFactory;
import edu.calpoly.csc365.example1.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomerCreateServlet", urlPatterns = "/create_customer")
public class CustomerCreateServlet extends HttpServlet {
    private DaoManager dm = null;
    private Dao<Customer> customerDao = null;

    public CustomerCreateServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        customerDao = dm.getCustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("customer_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        String ssn = request.getParameter("ssn");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Customer customer = new Customer(id, ssn, lastName, firstName, address, phone);
        id = this.customerDao.insert(customer);
        response.sendRedirect("edit_customer?id=" + id.toString());
    }
}