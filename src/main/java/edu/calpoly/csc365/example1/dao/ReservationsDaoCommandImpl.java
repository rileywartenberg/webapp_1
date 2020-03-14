package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Reservations;

import java.sql.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReservationsDaoCommandImpl implements DaoCommand {
    private Reservations reservations;

    public ReservationsDaoCommandImpl(Reservations reservations) {
        this.reservations = reservations;
    }

    @Override
    public String execute(DaoManager daoManager) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String message = null;
        Integer rowsAffected = 0;

        try {
            conn = daoManager.getTransConnection();
            preparedStatement = conn.prepareStatement(
                  "INSERT INTO Reservations (cid, room, checkIn, checkout, rate, adults, kids, ccnum) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, reservations.getCid());
            preparedStatement.setString(2, reservations.getRoom());
            preparedStatement.setDate(3, reservations.getCheckin());
            preparedStatement.setDate(4, reservations.getCheckout());
            preparedStatement.setDouble(5, reservations.getRate());
            preparedStatement.setInt(6, reservations.getAdults());
            preparedStatement.setInt(7, reservations.getKids());
            preparedStatement.setInt(8, reservations.getCcnum());
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                message = "0";
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                    reservations.setId(resultSet.getInt(1));
            }
            else
                message = "1";
        } catch (SQLException e) {
            message = "runError";
            if(e.getSQLState().equals("12345"))
                message = e.getMessage();
            else
            {
                e.printStackTrace();
                throw new RuntimeException("Error occurred during SQL execution!");
            }
        }
        finally {
            return message;
        }
    }

    public String executeUpdate(DaoManager daoManager) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String message = null;
        Integer rowsAffected = 0;

        try {
            conn = daoManager.getTransConnection();
            preparedStatement = conn.prepareStatement(
                    "UPDATE Reservations SET room=?, checkin=?, checkout=?, rate=?," +
                          "adults=?, kids=?, ccnum=? WHERE id=?");
            preparedStatement.setInt(1, reservations.getCid());
            preparedStatement.setString(2, reservations.getRoom());
            preparedStatement.setDate(3, reservations.getCheckin());
            preparedStatement.setDate(4, reservations.getCheckout());
            preparedStatement.setDouble(5, reservations.getRate());
            preparedStatement.setInt(6, reservations.getAdults());
            preparedStatement.setInt(7, reservations.getKids());
            preparedStatement.setInt(8, reservations.getCcnum());
            preparedStatement.setInt(9, reservations.getId());
            rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0)
                message = "0";
            else
                message = "1";
        } catch (SQLException e) {
            message = "runError";
            if(e.getSQLState().equals("12345"))
                message = e.getMessage();
            else {
                e.printStackTrace();
                throw new RuntimeException("Error occurred during SQL execution!");
            }
        }
        finally {
            return message;
        }
    }

    public String executeDelete(DaoManager daoManager) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer rowsAffected = 0;
        String message = null;

        try {
            conn = daoManager.getTransConnection();
            preparedStatement = conn.prepareStatement(
                    "DELETE FROM Reservations WHERE id = ?");
            preparedStatement.setInt(1, reservations.getId());
            System.out.println("wh");
            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("works");
            if(rowsAffected > 0)
                message = "0";
            else
                message = "1";
        } catch (SQLException e) {
            message = "runError";
            if(e.getSQLState().equals("12345"))
                message = e.getMessage();
            else {
                e.printStackTrace();
                throw new RuntimeException("Error occurred during SQL execution!");
            }
        }
        finally {
            return message;
        }
    }
}