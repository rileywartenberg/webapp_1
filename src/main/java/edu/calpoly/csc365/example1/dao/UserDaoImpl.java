package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.CreditCard;
import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDao {
  Connection conn = null;

  public UserDaoImpl(Connection conn) {
    this.conn = conn;
  }


    public Set<User> getAllById(int id) { return null;}

  @Override
  public Boolean authenticate(String name, String pass) {
    Boolean authenticated = false;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT name FROM Users WHERE name = ? AND pass = ?");
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, pass);
      resultSet = preparedStatement.executeQuery();
      System.out.println(name + " yes  " + pass);
      if (resultSet != null && resultSet.first()) authenticated = true;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return authenticated;
  }
  public User getByName(String name)
  {
    User user = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Users WHERE name=?");
      preparedStatement.setString(1, name);
      resultSet = preparedStatement.executeQuery();
      Set<User> users = unpackResultSet(resultSet);
      user = (User)users.toArray()[0];
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
    public Set<User> getByDate(Date date)
    {
        return null;
    }
  @Override
  public User getById(int id) {
    return null;
  }

  @Override
  public Set<User> getAll() {
    return null;
  }

    @Override
    public Integer insert(User obj) {
        Integer id = 1;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Users (name, pass, cid) VALUES (?, ?, ?)");
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getPass());
            preparedStatement.setInt(3, obj.getCid());
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
  public Integer update(User obj) {
    return null;
  }

  @Override
  public Integer delete(User obj) {
    return null;
  }

  private Set<User> unpackResultSet(ResultSet rs) throws SQLException {
    Set<User> users = new HashSet<User>();

    while(rs.next()) {
      User user = new User(
              rs.getInt("cid"),
              rs.getString("name"),
              rs.getString("pass")
      );
      users.add(user);
    }
    return users;
  }
}
