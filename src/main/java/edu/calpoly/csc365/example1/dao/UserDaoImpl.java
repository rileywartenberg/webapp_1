package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class UserDaoImpl implements UserDao {
  Connection conn = null;

  public UserDaoImpl(Connection conn) {
    this.conn = conn;
  }

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
      if (resultSet != null && resultSet.first()) authenticated = true;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return authenticated;
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
    Integer id = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement(
              "INSERT INTO Users (name, pass, cid) VALUES (?, ?, ?)");
      preparedStatement.setString(1, obj.getName());
      preparedStatement.setString(2, obj.getPass());
      preparedStatement.setInt(3, obj.getCid());
      int numRows = preparedStatement.executeUpdate();
      if (numRows == 1) {
        // get generated id
        resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next())
          id = resultSet.getInt(3);

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
  public Integer update(User obj) {
    return null;
  }

  @Override
  public Integer delete(User obj) {
    return null;
  }
}
