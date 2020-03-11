package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Reservations;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.sql.Date;


public class ReservationsDaoImpl implements Dao<Reservations> {
    private Connection conn;

    public ReservationsDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Reservations getById(int id) {
        Reservations reservations = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE rid=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Set<Reservations> reservationss = unpackResultSet(resultSet);
            reservations = (Reservations)reservationss.toArray()[0];
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reservations;
    }

    @Override
    public Set<Reservations> getAll() {
        Set<Reservations> reservationss = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations");
            resultSet = preparedStatement.executeQuery();
            reservationss = unpackResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reservationss;
    }

    @Override
    public Integer insert(Reservations obj) {
        Integer id = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Reservations (id, cid, room, checkin, checkout,rate,adults,kids) VALUES (?, ?, ?, ?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, obj.getCid());
            preparedStatement.setInt(2, obj.getId());
            preparedStatement.setString(3, obj.getRoom());
            preparedStatement.setDate(4, obj.getCheckin());
            preparedStatement.setDate(5, obj.getCheckout());
            preparedStatement.setDouble(6, obj.getRate());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 1) {
                // get generated id
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                    id = resultSet.getInt(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public Integer update(Reservations obj) {
        Integer numRows = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Reservations SET cid=?, room=?, checkin=?, checkout=?, rate=?,adults=?,kids=? WHERE id=?");
            preparedStatement.setInt(1, obj.getCid());
            preparedStatement.setInt(2, obj.getId());
            preparedStatement.setString(3, obj.getRoom());
            preparedStatement.setDate(4, obj.getCheckin());
            preparedStatement.setDate(5, obj.getCheckout());
            preparedStatement.setDouble(6, obj.getRate());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return numRows;
        } finally{
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRows;
    }

    @Override
    public Integer delete(Reservations obj) {
        Integer numRows = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.conn.prepareStatement("DELETE FROM Reservations WHERE id = ?");
            preparedStatement.setInt(1, obj.getId());
            numRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRows;
    }

    private Set<Reservations> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Reservations> reservationss = new HashSet<Reservations>();

        while(rs.next()) {
            Reservations reservations = new Reservations(
                    rs.getInt("cid"),
                            rs.getInt("id"),


                    rs.getString("room"),
                    rs.getDate("checkin"),
                            rs.getDate("checkout"),
                    rs.getDouble("rate"),
                    rs.getInt("adults"),
            rs.getInt("kids"));
            reservationss.add(reservations);
        }
        return reservationss;
    }



}
