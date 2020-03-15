package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Availability;
import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Rooms;

import java.sql.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class RoomAvailabilityDaoImpl implements Dao<Availability> {
    private Connection conn;

    public RoomAvailabilityDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Availability getByName(String name) { return null;}

    public Set<Availability> getAllById(int id) { return null;}

    public Availability getByRoomName(String room) {
        Availability a = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms WHERE roomId=?");
            preparedStatement.setString(1, room);
            resultSet = preparedStatement.executeQuery();
            Set<Availability> availabilities = unpackResultSet(resultSet);
            a = (Availability) availabilities.toArray()[0];
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
        return a;
    }

    public Set<Availability> getByEverything(Date checkin, Date checkout, Double minPrice, Double maxPrice, String bedType, Integer beds, Integer maxOcc) {
        Calendar cal;
        Set<Availability> availabilities= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT a.RId as roomId, a.roomName, a.basePrice, \\n\" +\n" +
                    "        \"a.bedType, a.beds, a.maxOccupancy, IFNULL(b.Popularity,0) as \\n\" +\n" +
                    "        \"popularity, IFNULL(c.Availability, \\\"Available\\\") as Availability,\\n\" +\n" +
                    "        \"IFNULL(c.length, 0) as length\\n\" +\n" +
                    "        \"FROM\\n\" +\n" +
                    "        \"(\\n\" +\n" +
                    "        \"SELECT DISTINCT\\n\" +\n" +
                    "        \"r.roomId as rId, \\n\" +\n" +
                    "        \"r.roomName as roomName, \\n\" +\n" +
                    "        \"r.basePrice as basePrice, \\n\" +\n" +
                    "        \"/*Case statements to check availability*/\\n\" +\n" +
                    "        \"r.bedType as bedType, r.beds as beds, r.maxOccupancy as maxOccupancy\\n\" +\n" +
                    "        \"\\n\" +\n" +
                    "        \"FROM \\n\" +\n" +
                    "        \"rooms as r \\n\" +\n" +
                    "        \"\\n\" +\n" +
                    "        \")a\\n\" +\n" +
                    "        \"\\n\" +\n" +
                    "        \"LEFT JOIN \\n\" +\n" +
                    "        \"(\\n\" +\n" +
                    "        \"SELECT DISTINCT rid, roomName, SUM(Days)/180 as Popularity\\n\" +\n" +
                    "        \"FROM\\n\" +\n" +
                    "        \"(\\n\" +\n" +
                    "        \"SELECT re.room as rid, r.roomName as roomName, \\n\" +\n" +
                    "        \"CASE WHEN (DATE_SUB(?, INTERVAL 180 DAY) <= re.checkout\\n\" +\n" +
                    "        \"AND DATE_SUB(?, INTERVAL 180 DAY) >= re.checkIn) \\n\" +\n" +
                    "        \"THEN DATEDIFF(re.checkout, DATE_SUB(?, INTERVAL 180 DAY)) \\n\" +\n" +
                    "        \"WHEN (DATE_SUB(?, INTERVAL 180 DAY) <= re.checkIn\\n\" +\n" +
                    "        \"AND ? >= checkOut)\\n\" +\n" +
                    "        \"THEN DATEDIFF(re.checkout, re.checkIn)\\n\" +\n" +
                    "        \"ELSE DATEDIFF(?, checkIn)\\n\" +\n" +
                    "        \"END as Days\\n\" +\n" +
                    "        \"FROM \\n\" +\n" +
                    "        \"Reservations as re JOIN rooms as r \\n\" +\n" +
                    "        \"ON re.room = r.roomID\\n\" +\n" +
                    "        \"WHERE \\n\" +\n" +
                    "        \"(DATE_SUB(?, INTERVAL 180 DAY) <= re.checkout\\n\" +\n" +
                    "        \"AND ? >= re.checkIn) \\n\" +\n" +
                    "        \")a \\n\" +\n" +
                    "        \"GROUP BY rid, roomName\\n\" +\n" +
                    "        \"ORDER BY Popularity\\n\" +\n" +
                    "        \")b\\n\" +\n" +
                    "        \"ON a.rId = b.rid \\n\" +\n" +
                    "        \"LEFT JOIN(\\n\" +\n" +
                    "        \"/*Case 1: If the minimum checkin date that is greater than the wanted date\\n\" +\n" +
                    "        \"then the next available */\\n\" +\n" +
                    "        SELECT DISTINCT re.room as rId, re.checkIn, re.checkout, \n" +
                            "DATEDIFF(checkout, checkIn) as length,\n" +
                            "CASE WHEN (? >=re.checkIn AND ? < re.checkout\n" +
                            "OR ? > re.checkIn AND ? < re.checkout OR \n" +
                            "(? < re.checkIn AND ? >= re.checkout))\n" +
                            "THEN \"Not Available\"\n" +
                            "ELSE \"Available\"\n" +
                            "END as Availability\n" +
                            "FROM \n" +
                            "Reservations as re JOIN rooms as r \n" +
                            "ON re.room = r.roomID\n" +
                            "WHERE (? >=re.checkIn AND ? < re.checkout\n" +
                    "OR ? > re.checkIn AND ? < re.checkout) OR \n" +
                    "(? < re.checkIn AND ? >= re.checkout)\n" +
                    ")c \n" +
                    "ON c.rId = a.rId\n" +
                    "WHERE\n" +
                    "a.bedType = ?\n" +
                    "AND a.beds = ?\n" +
                    "AND a.basePrice BETWEEN ? AND ?\n" +
                    "AND a.maxOccupancy >= ?;");
            preparedStatement.setDate(1, checkin);
            preparedStatement.setDate(2, checkin);
            preparedStatement.setDate(3, checkin);
            preparedStatement.setDate(4, checkin);
            preparedStatement.setDate(5, checkin);
            preparedStatement.setDate(6, checkin);
            preparedStatement.setDate(7, checkin);
            preparedStatement.setDate(8, checkin);

            preparedStatement.setDate(9, checkin);
            preparedStatement.setDate(10, checkin);
            preparedStatement.setDate(11, checkout);
            preparedStatement.setDate(12, checkout);
            preparedStatement.setDate(13, checkin);
            preparedStatement.setDate(14, checkout);

            preparedStatement.setDate(15, checkin);
            preparedStatement.setDate(16, checkin);
            preparedStatement.setDate(17, checkout);
            preparedStatement.setDate(18, checkout);
            preparedStatement.setDate(19, checkin);
            preparedStatement.setDate(20, checkout);

            preparedStatement.setString(21, bedType);
            preparedStatement.setInt(22,  beds);
            preparedStatement.setDouble(23, minPrice);
            preparedStatement.setDouble(24, maxPrice);
            preparedStatement.setInt(25, maxOcc);

            resultSet = preparedStatement.executeQuery();
            availabilities = unpackResultSet(resultSet);

            for(int i = 0; i < availabilities.size(); i++){
                int add = 0;
                Set<Availability> agets;
                Availability atest = ((Availability)(availabilities.toArray()[i]));
                boolean test = atest.getAvailable().equals("Not Available");
                while(test)
                {

                    preparedStatement = this.conn.prepareStatement("SELECT a.roomName as roomName, \n" +
                            "IFNULL(b.Availability, \"Available\") as Availability, DATE_ADD(?, INTERVAL ? DAY) as day\n" +
                            "FROM\n" +
                            "(\n" +
                            "SELECT \n" +
                            "r.roomId as rId, \n" +
                            "r.roomName as roomName\n" +
                            "FROM rooms as r \n" +
                            "WHERE r.roomName = ?\n" +
                            ")a\n" +
                            "LEFT JOIN\n" +
                            "(\n" +
                            "SELECT DISTINCT re.room as rId, \n" +
                            "CASE WHEN (DATE_ADD(?, INTERVAL ? DAY)\n" +
                            ">=re.checkIn AND \n" +
                            "DATE_ADD(?, INTERVAL ? DAY) < re.checkout)\n" +
                            "THEN \"Not Available\"\n" +
                            "ELSE \"Available\"\n" +
                            "END as Availability\n" +
                            "FROM \n" +
                            "Reservations as re JOIN rooms as r ON re.room = r.roomID \n" +
                            "WHERE (DATE_ADD(?, INTERVAL ? DAY) >=re.checkIn AND \n" +
                            "DATE_ADD(?, INTERVAL ? DAY) < re.checkout)\n" +
                            "AND r.roomName = ?\n" +
                            ")b\n" +
                            "ON a.rId = b.rId;");

                    preparedStatement.setDate(1, checkin);
                    preparedStatement.setInt(2, add);
                    preparedStatement.setString(3, atest.getRoomName());
                    preparedStatement.setDate(4, checkin);
                    preparedStatement.setInt(5, add);
                    preparedStatement.setDate(6, checkin);
                    preparedStatement.setInt(7, add);
                    preparedStatement.setDate(8, checkin);
                    preparedStatement.setInt(9, add);
                    preparedStatement.setDate(10, checkin);
                    preparedStatement.setInt(11, add);
                    preparedStatement.setString(12, atest.getRoomName());

                    resultSet = preparedStatement.executeQuery();
                    System.out.println("hi");
                    resultSet.next();
                    test = resultSet.getString("Availability").equals("Not Available");
                    System.out.println(resultSet.getString("Availability"));
                    add += 1;
                }
                if (add == 0)
                {
                    atest.setNextDate(checkin);
                }

                else{
                    System.out.println(add);
                    System.out.println(resultSet.getDate("day", Calendar.getInstance()));
                    atest.setNextDate(resultSet.getDate("day", Calendar.getInstance()));
                }

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
        return availabilities;
    }

    @Override
    public Availability getById(int id) {
        Availability a = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms WHERE roomId=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Set<Availability> availabilities = unpackResultSet(resultSet);
            a = (Availability) availabilities.toArray()[0];
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
        return a;
    }

    public Set<Availability> getByDate(Date date) {
        Calendar cal;
        Set<Availability> availabilities= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
"SELECT a.RId as roomId, a.roomName, a.basePrice, \n" +
        "a.bedType, a.beds, a.maxOccupancy, IFNULL(b.Popularity,0) as \n" +
        "popularity, IFNULL(c.Availability, \"Available\") as Availability,\n" +
        "IFNULL(c.length, 0) as length\n" +
        "FROM\n" +
        "(\n" +
        "SELECT DISTINCT\n" +
        "r.roomId as rId, \n" +
        "r.roomName as roomName, \n" +
        "r.basePrice as basePrice, \n" +
        "/*Case statements to check availability*/\n" +
        "r.bedType as bedType, r.beds as beds, r.maxOccupancy as maxOccupancy\n" +
        "\n" +
        "FROM \n" +
        "rooms as r \n" +
        "\n" +
        ")a\n" +
        "\n" +
        "LEFT JOIN \n" +
        "(\n" +
        "SELECT DISTINCT rid, roomName, SUM(Days)/180 as Popularity\n" +
        "FROM\n" +
        "(\n" +
        "SELECT re.room as rid, r.roomName as roomName, \n" +
        "CASE WHEN (DATE_SUB(?, INTERVAL 180 DAY) <= re.checkout\n" +
        "AND DATE_SUB(?, INTERVAL 180 DAY) >= re.checkIn) \n" +
        "THEN DATEDIFF(re.checkout, DATE_SUB(?, INTERVAL 180 DAY)) \n" +
        "WHEN (DATE_SUB(?, INTERVAL 180 DAY) <= re.checkIn\n" +
        "AND ? >= checkOut)\n" +
        "THEN DATEDIFF(re.checkout, re.checkIn)\n" +
        "ELSE DATEDIFF(?, checkIn)\n" +
        "END as Days\n" +
        "FROM \n" +
        "Reservations as re JOIN rooms as r \n" +
        "ON re.room = r.roomID\n" +
        "WHERE \n" +
        "(DATE_SUB(?, INTERVAL 180 DAY) <= re.checkout\n" +
        "AND ? >= re.checkIn) \n" +
        ")a \n" +
        "GROUP BY rid, roomName\n" +
        "ORDER BY Popularity\n" +
        ")b\n" +
        "ON a.rId = b.rid \n" +
        "LEFT JOIN(\n" +
        "/*Case 1: If the minimum checkin date that is greater than the wanted date\n" +
        "then the next available */\n" +
        "\n" +
        "\n" +
        "SELECT DISTINCT re.room as rId, re.checkIn, re.checkout, \n" +
        "DATEDIFF(checkout, checkIn) as length,\n" +
        "CASE WHEN (? >=re.checkIn AND ? < re.checkout)\n" +
        "THEN \"Not Available\"\n" +
        "ELSE \"Available\"\n" +
        "END as Availability\n" +
        "FROM \n" +
        "Reservations as re JOIN rooms as r ON re.room = r.roomID \n" +
        "WHERE (? >=re.checkIn AND ? < re.checkout)\n" +
        ")c \n" +
        "ON c.rId = a.rId;\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n");
            preparedStatement.setDate(1, date);
            preparedStatement.setDate(2, date);
            preparedStatement.setDate(3, date);
            preparedStatement.setDate(4, date);
            preparedStatement.setDate(5, date);
            preparedStatement.setDate(6, date);
            preparedStatement.setDate(7, date);
            preparedStatement.setDate(8, date);
            preparedStatement.setDate(9, date);
            preparedStatement.setDate(10, date);
            preparedStatement.setDate(11, date);
            preparedStatement.setDate(12, date);


            resultSet = preparedStatement.executeQuery();
            availabilities = unpackResultSet(resultSet);

            for(int i = 0; i < availabilities.size(); i++){
                int add = 0;
                Set<Availability> agets;
                Availability atest = ((Availability)(availabilities.toArray()[i]));
                boolean test = atest.getAvailable().equals("Not Available");
                while(test)
                {

                    preparedStatement = this.conn.prepareStatement("SELECT a.roomName as roomName, \n" +
                            "IFNULL(b.Availability, \"Available\") as Availability, DATE_ADD(?, INTERVAL ? DAY) as day\n" +
                            "FROM\n" +
                            "(\n" +
                            "SELECT \n" +
                            "r.roomId as rId, \n" +
                            "r.roomName as roomName\n" +
                            "FROM rooms as r \n" +
                            "WHERE r.roomName = ?\n" +
                            ")a\n" +
                            "LEFT JOIN\n" +
                            "(\n" +
                            "SELECT DISTINCT re.room as rId, \n" +
                            "CASE WHEN (DATE_ADD(?, INTERVAL ? DAY)\n" +
                            ">=re.checkIn AND \n" +
                            "DATE_ADD(?, INTERVAL ? DAY) < re.checkout)\n" +
                            "THEN \"Not Available\"\n" +
                            "ELSE \"Available\"\n" +
                            "END as Availability\n" +
                            "FROM \n" +
                            "Reservations as re JOIN rooms as r ON re.room = r.roomID \n" +
                            "WHERE (DATE_ADD(?, INTERVAL ? DAY) >=re.checkIn AND \n" +
                            "DATE_ADD(?, INTERVAL ? DAY) < re.checkout)\n" +
                            "AND r.roomName = ?\n" +
                            ")b\n" +
                            "ON a.rId = b.rId;");

                    preparedStatement.setDate(1, date);
                    preparedStatement.setInt(2, add);
                    preparedStatement.setString(3, atest.getRoomName());
                    preparedStatement.setDate(4, date);
                    preparedStatement.setInt(5, add);
                    preparedStatement.setDate(6, date);
                    preparedStatement.setInt(7, add);
                    preparedStatement.setDate(8, date);
                    preparedStatement.setInt(9, add);
                    preparedStatement.setDate(10, date);
                    preparedStatement.setInt(11, add);
                    preparedStatement.setString(12, atest.getRoomName());

                    resultSet = preparedStatement.executeQuery();
                    System.out.println("hi");
                    resultSet.next();
                    test = resultSet.getString("Availability").equals("Not Available");
                    System.out.println(resultSet.getString("Availability"));
                    add += 1;
                }
                if (add == 0)
                {
                    atest.setNextDate(date);
                }

                else{
                    System.out.println(add);
                    System.out.println(resultSet.getDate("day", Calendar.getInstance()));
                    atest.setNextDate(resultSet.getDate("day", Calendar.getInstance()));
                }

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
        return availabilities;
    }


    @Override
    public Integer delete(Availability obj) {
        return null;
    }

    @Override
    public Set<Availability> getAll() {
        Set<Availability> availabilities= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM rooms");
            resultSet = preparedStatement.executeQuery();
            availabilities = unpackResultSet(resultSet);
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
        return availabilities;
    }

    @Override
    public Integer insert(Availability obj) {
        return null;
    }

    @Override
    public Integer update(Availability obj) {
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(
                    "UPDATE rooms SET roomName=?, beds=?, bedType=?, maxOccupancy=?, basePrice=?, decor=? WHERE roomId=?");
            preparedStatement.setString(1, obj.getRoomName());
            preparedStatement.setInt(2, obj.getBeds());
            preparedStatement.setString(3, obj.getBedType());
            preparedStatement.setDouble(4, obj.getMaxOccupancy());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }


    private Set<Availability> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Availability> availabilities = new HashSet<>();

        while (rs.next()) {
            Availability a = new Availability(
                null,
                rs.getString("roomName"),
                rs.getDouble("popularity"),
                rs.getDouble("basePrice"),
                rs.getString("Availability"),
                null,
                rs.getInt("length"),
                rs.getInt("beds"),
                rs.getString("bedType"),
                rs.getInt("maxOccupancy"));
            availabilities.add(a);
        }
        return availabilities;
    }

    /*
                        rs.getString("roomId"),
                                rs.getString("roomName"),
                                rs.getInt("beds"),
                                rs.getString("bedType"),
                                rs.getInt("maxOccupancy"),
                                rs.getDouble("basePrice"),
                                rs.getString("decor")
                                */

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
