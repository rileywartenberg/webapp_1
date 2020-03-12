package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Rooms;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RoomsDaoImpl implements Dao<Rooms> {
    private Connection conn;

    public RoomsDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Rooms getByName(String name) { return null;}

    public Set<Rooms> getAllById(int id) { return null;}

    public Rooms getByRoomName(String room) {
        Rooms r = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms WHERE roomId=?");
            preparedStatement.setString(1, room);
            resultSet = preparedStatement.executeQuery();
            Set<Rooms> rooms = unpackResultSet(resultSet);
            r = (Rooms) rooms.toArray()[0];
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
        return r;
    }



    @Override
    public Rooms getById(int id) {
        Rooms r = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms WHERE roomId=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Set<Rooms> rooms = unpackResultSet(resultSet);
            r = (Rooms) rooms.toArray()[0];
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
        return r;
    }

    @Override
    public Set<Rooms> getAll() {
        Set<Rooms> rooms = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms");
            resultSet = preparedStatement.executeQuery();
            rooms = unpackResultSet(resultSet);
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
        return rooms;
    }

    @Override
    public Integer insert(Rooms obj) {
        return null;
    }

    @Override
    public Integer update(Rooms obj) {
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(
                    "UPDATE rooms SET roomName=?, beds=?, bedType=?, maxOccupancy=?, basePrice=?, decor=? WHERE roomId=?");
            preparedStatement.setString(1, obj.getRoomName());
            preparedStatement.setInt(2, obj.getBeds());
            preparedStatement.setString(3, obj.getBedType());
            preparedStatement.setDouble(4, obj.getMaxOccupancy());
            preparedStatement.setDouble(5, obj.getBasePrice());
            preparedStatement.setString(5, obj.getDecor());
            preparedStatement.setString(5, obj.getRoomId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public Integer delete (Rooms obj) {
        return null;
    }

    private Set<Rooms> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Rooms> rooms = new HashSet<Rooms>();

        while (rs.next()) {
            Rooms r = new Rooms(
                    rs.getString("roomId"),
                    rs.getString("roomName"),
                    rs.getInt("beds"),
                    rs.getString("bedType"),
                    rs.getInt("maxOccupancy"),
                    rs.getDouble("basePrice"),
                    rs.getString("decor")
            );
            rooms.add(r);
        }
        return rooms;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}