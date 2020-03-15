package edu.calpoly.csc365.example1.dao;

import edu.calpoly.csc365.example1.entity.Report;
import java.util.Set;


public interface ReportDao extends Dao<Report> {
    public Set<Report> display();
}
