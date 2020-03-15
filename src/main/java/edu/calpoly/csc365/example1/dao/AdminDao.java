package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Admin;

public interface AdminDao extends Dao<Admin> {
    public Boolean authenticate(String name, String pass);
}
