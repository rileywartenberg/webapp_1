package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AdminDaoImpl implements AdminDao {
    Connection conn = null;

    public AdminDaoImpl(Connection conn) {
        this.conn = conn;
    }


    public Set<Admin> getAllById(int id) { return null;}

    @Override
    public Boolean authenticate(String name, String pass) {
        Boolean authenticated = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT name FROM Admin WHERE name = ? AND pass = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            System.out.println(name + " " + pass);
            if (resultSet != null && resultSet.first()) authenticated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authenticated;
    }
    public Admin getByName(String name)
    {
        Admin user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Users WHERE name=?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            Set<Admin> users = unpackResultSet(resultSet);
            user = (Admin)users.toArray()[0];
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
    public Admin getById(int id) {
        return null;
    }

    @Override
    public Set<Admin> getAll() {
        return null;
    }

    @Override
    public Integer insert(Admin obj) {
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
    }

    @Override
    public Integer update(Admin obj) {
        return null;
    }

    @Override
    public Integer delete(Admin obj) {
        return null;
    }

    private Set<Admin> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Admin> users = new HashSet<Admin>();

        while(rs.next()) {
            Admin user = new Admin(
                    rs.getString("name"),
                    rs.getString("pass")
            );
            users.add(user);
        }
        return users;
    }
}
