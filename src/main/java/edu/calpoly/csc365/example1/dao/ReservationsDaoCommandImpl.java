package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Reservations;

import java.sql.*;

public class ReservationsDaoCommandImpl implements DaoCommand {
    private Reservations reservations;

    public ReservationsDaoCommandImpl(Reservations reservations) {
        this.reservations = reservations;
    }

    @Override
    public Object execute(DaoManager daoManager) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer rowsAffected = 0;
        try {
            conn = daoManager.getConnection();
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO Reservations (id, cid, room, checkin, checkout,rate,adults,kids) VALUES (?, ?, ?, ?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, reservations.getId());
            preparedStatement.setInt(2, reservations.getCid());
            preparedStatement.setString(3, reservations.getRoom());
            preparedStatement.setDate(4, reservations.getCheckin());
            preparedStatement.setDate(5, reservations.getCheckout());
            preparedStatement.setDouble(6, reservations.getRate());
            preparedStatement.setInt(7, reservations.getAdults());
            preparedStatement.setInt(8, reservations.getKids());
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                    reservations.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred during SQL execution!");
        }
        return reservations;
    }
}