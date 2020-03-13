package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Customer;
import edu.calpoly.csc365.example1.entity.Reservations;
import edu.calpoly.csc365.example1.entity.Rooms;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoManager {

  protected DataSource dataSource = null;
  protected Connection conn = null;

  public DaoManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Connection getConnection() throws SQLException {
    if (this.conn == null) {
      this.conn = this.dataSource.getConnection();
    }
    return this.conn;
  }

  public Connection getTransConnection() throws SQLException {
    if (this.conn == null) {
      this.getConnection().setAutoCommit(false);
    }
    return this.conn;
  }

  public void close() throws SQLException {
    try
    {
      if(this.conn != null && !this.conn.isClosed())
        this.conn.close();
    }
    catch(SQLException e) { throw e; }
  }

  public Object transaction(DaoCommand command){
    try{
      this.conn.setAutoCommit(false);
      System.out.println("yes");
      Object returnValue = command.execute(this);
      System.out.println("no");
      System.out.println(returnValue);
      this.conn.commit();
      return returnValue;
    } catch(Exception e){
      try {
        this.conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } finally {
      try {
        this.conn.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

    public Object transactionUpdate(DaoCommand command){
        try{
            this.conn.setAutoCommit(false);
            System.out.println("yes");
            Object returnValue = ((ReservationsDaoCommandImpl)command).executeUpdate(this);
            System.out.println("\no");
            System.out.println(returnValue);
            this.conn.commit();
            return returnValue;
        } catch(Exception e){
            try {
                this.conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object transactionDelete(DaoCommand command){
        try{
            this.conn.setAutoCommit(false);
            System.out.println("yesss");
            Object returnValue = ((ReservationsDaoCommandImpl)command).executeDelete(this);
            System.out.println("nooo");
            System.out.println("  return");
            this.conn.commit();
            return returnValue;
        } catch(Exception e){
            try {
                this.conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

  public Object executeAndClose(DaoCommand command){
    try{
      return command.execute(this);
    } finally {
      try {
        this.getConnection().close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Object transactionAndClose(final DaoCommand command){
    return executeAndClose(new DaoCommand() {
      @Override
      public Object execute(DaoManager daoManager) {
        return daoManager.transaction(command);
      }
    });
  }

  public Dao<Customer> getCustomerDao() throws SQLException {
    return new CustomerDaoImpl(this.getConnection());
  }

  public Dao<Reservations> getReservationsDao() throws SQLException {
    return new ReservationsDaoImpl(this.getConnection());
  }

  public UserDao getUserDao() throws SQLException {
    return new UserDaoImpl(this.getConnection());
  }

    public AdminDao getAdminDao() throws SQLException {
        return new AdminDaoImpl(this.getConnection());
    }

  public Dao<Rooms> getRoomsDao() throws SQLException {
    return new RoomsDaoImpl(this.getConnection());
  }
}
