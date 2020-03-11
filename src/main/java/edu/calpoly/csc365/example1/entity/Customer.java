package edu.calpoly.csc365.example1.entity;

public class Customer {
  private Integer id;
  private String ssn;
  private String lastName;
  private String firstName;
  private String address;
  private String phone;

  public Customer(){
    this.id = null;
    this.ssn = null;
    this.lastName = null;
    this.firstName = null;
    this.address = null;
    this.phone = null;
  }

  public Customer(String ssn, String lastName, String firstName, String address, String phone) {
    this.id = null;
    this.ssn = ssn;
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.phone = phone;
  }

  public Customer(Integer id, String ssn, String lastName, String firstName, String address, String phone) {
    this.id = id;
    this.ssn = ssn;
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.phone = phone;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "id: " + id.toString() + ", ssn: " + ssn.toString() + ", name: " + lastName
      + ", address: " + address + ", phone: " + phone;
  }
}
