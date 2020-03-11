package edu.calpoly.csc365.example1.controller;

import edu.calpoly.csc365.example1.dao.*;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Reservations;

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



@WebServlet(name = "ReservationsCreateServlet", urlPatterns = "/create_reservations")
public class ReservationsCreateServlet extends HttpServlet {
    private DaoManager dm; //= null;
    private Dao<Reservations> reservationsDao;// = null;

    public ReservationsCreateServlet() throws Exception {
        dm = DaoManagerFactory.createDaoManager();
        reservationsDao = dm.getReservationsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reservations_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        String room = request.getParameter("room");
       // try {
            Date checkin = Date.valueOf(request.getParameter("checkin"));
            System.out.println(checkin);
      //  }
      //  catch (Exception e) {

       // System.out.print("\n\n\n\n\nwhat" );
       // }
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        System.out.println(checkout);

        Double rate = Double.parseDouble(request.getParameter("rate"));
        Integer adults = Integer.parseInt(request.getParameter("adults"));
        Integer kids = Integer.parseInt(request.getParameter("kids"));



        /*String id = request.getParameter("id");
        String cid = request.getParameter("cid");
        String room = request.getParameter("room");
        String checkin = request.getParameter("checkin");

        String checkout = request.getParameter("checkout");
        System.out.print("\n\n\n\n" + checkin + checkout + "\n\n\n\n\n");
        String rate = request.getParameter("rate");
        String adults = request.getParameter("adults");
        String kids = request.getParameter("kids");

        Date cout = new Date(2000,11,11);
        Date cin = new Date(2000,11,12);


        try
        {

            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            cin = (Date)formatter.parse(checkin);
        }
        catch (Exception e)
        {}


        try
        {

            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy/MM/dd");
             cout = (Date)formatter.parse(checkout);
        }
        catch (Exception e)
        {}
                Reservations reservations = new Reservations(id,Integer.parseInt(cid),room,cin,cout,Double.parseDouble(rate),Integer.parseInt(adults),Integer.parseInt(kids));
        id = this.reservationsDao.insert(reservations);
        response.sendRedirect("edit_reservations?id=" + id.toString());
*/
        Reservations reservations = new Reservations();
        reservations.setCid(cid);
        reservations.setId(id);
        reservations.setRoom(room);
        reservations.setCheckin(checkin);
        reservations.setCheckout(checkout);
        reservations.setRate(rate);
        reservations.setAdults(adults);
        reservations.setKids(kids);


        DaoCommand daoCommand = new ReservationsDaoCommandImpl(reservations);
        Object result = daoCommand.execute(this.dm);
        if (result != null) {
            reservations = (Reservations) result;
        }
        PrintWriter out = response.getWriter();
        out.println(reservations);
        out.close();
        
        
    //    id = this.reservationsDao.insert(reservations);
      //  response.sendRedirect("edit_reservations?id=" + id.toString());
    }
}