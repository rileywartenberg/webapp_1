package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Report;
import edu.calpoly.csc365.example1.entity.Report;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ReportDaoImpl implements ReportDao {
    Connection conn = null;

    public ReportDaoImpl(Connection conn) {
        this.conn = conn;
    }


    public Set<Report> getAllById(int id) {
        return null;
    }

        public Set<Report> display() {
            Set<Report> reports = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = this.conn.prepareStatement("with jan as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) January\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 1\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "feb as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) February\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 2\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "mar as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) March\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 3\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "apr as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) April\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 4\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "may as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) May\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 5\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "june as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) June\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 6\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "july as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) July\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 7\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "aug as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) August\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 8\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "sept as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) September\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 9\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "oct as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) October\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 10\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "nov as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) November\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 11\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "december as \n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) December\n" +
                        "from Reservations\n" +
                        "where date_format(checkout,'%c') = 12\n" +
                        "group by \n" +
                        "date_format(checkout,'%c')\n" +
                        ",room),\n" +
                        "\n" +
                        "total as\n" +
                        "(select room,\n" +
                        "sum(rate * datediff(checkout,checkin)) tot\n" +
                        "from Reservations\n" +
                        "group by \n" +
                        "room)\n" +
                        "\n" +
                        "\n" +
                        "select roomname ,january,february,march,april,may,june,july,august,september,october,november,december, tot total\n" +
                        " from \n" +
                        "rooms left join jan \n" +
                        "on jan.room = roomid \n" +
                        "left join feb on feb.room = roomid\n" +
                        "left join mar on mar.room = roomid\n" +
                        "left join apr on apr.room = roomid\n" +
                        "left join may on may.room = roomid\n" +
                        "left join june on june.room = roomid\n" +
                        "left join july on july.room = roomid\n" +
                        "left join aug on aug.room = roomid\n" +
                        "left join sept on sept.room = roomid\n" +
                        "left join oct on oct.room = roomid\n" +
                        "left join nov on nov.room = roomid\n" +
                        "left join december on december.room = roomid\n" +
                        "left join total on total.room = roomid\n" +
                        "group by roomid,total,january,february,march,april,may,june,july,august,september,october,november,december\n" +
                        "\n" +
                        ";");
                resultSet = preparedStatement.executeQuery();
                reports = unpackResultSet(resultSet);
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
            return reports;
        }

    public Report getByName(String name) {
        Report user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Users WHERE name=?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            Set<Report> users = unpackResultSet(resultSet);
            user = (Report) users.toArray()[0];
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
        return user;
    }


    @Override
    public Report getById(int id) {
        return null;
    }

    @Override
    public Set<Report> getAll() {
        return null;
    }

    @Override
    public Set<Report> getByDate(Date date)
    {
        return null;
    }

    public Integer insert(Report obj){return 1;}
 /*   @Override
    public Integer insert(Report obj) {
        Integer id = 1;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Users (name, pass) VALUES (?, ?)");
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getPass());
            preparedStatement.executeUpdate();
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
    }*/

    @Override
    public Integer update(Report obj) {
        return null;
    }

    @Override
    public Integer delete(Report obj) {
        return null;
    }

    private Set<Report> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Report> users = new HashSet<Report>();

        while(rs.next()) {
            Report user = new Report(
                    rs.getString("roomname"),
                    rs.getDouble("january"),
                    rs.getDouble("february"),
                    rs.getDouble("march"),
                    rs.getDouble("april"),
                    rs.getDouble("may"),
                    rs.getDouble("june"),
                    rs.getDouble("july"),
                    rs.getDouble("august"),
                    rs.getDouble("september"),
                    rs.getDouble("october"),
                    rs.getDouble("november"),
                    rs.getDouble("december")

            );
            users.add(user);
        }
        return users;
    }
}
