package edu.calpoly.csc365.example1.entity;

public class User {
  private Integer cid;
  private String name;
  private String pass;

  public Integer getCid() { return this.cid; }

  public void setCid(Integer cid) { this.cid = cid;}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }
}
