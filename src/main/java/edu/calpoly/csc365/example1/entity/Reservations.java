package edu.calpoly.csc365.example1.entity;

import java.sql.Date;

public class Reservations {
    private Integer cid;
    private Integer id;
    private String room;
    private Date checkin;
    private Date checkout;
    private Double rate;
    private Integer adults;
    private Integer kids;
    private Integer ccnum;

    public Reservations() {
        this.cid = null;
        this.id = null;
        this.room = null;
        this.checkin = null;
        this.checkout = null;
        this.rate = null;
        this.adults = null;
        this.kids = null;
        this.ccnum = null;
    }

    public Reservations(Integer cid, Integer id, String room,
                        Date checkin, Date checkout, Double rate, Integer adults, Integer kids, Integer ccnum) {
        this.cid = cid;
        this.id = id;
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
        this.rate = rate;
        this.adults = adults;
        this.kids = kids;
        this.ccnum = ccnum;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }


    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getKids() {
        return kids;
    }

    public void setKids(Integer kids) {
        this.kids = kids;
    }

    public Integer getCcnum() {
        return ccnum;
    }

    public void setCcnum(Integer ccnum) {
        this.ccnum = ccnum;
    }

    public String toString() {
        return "cid: " + cid.toString() + ", id: " + id.toString() + ", room: " + room + " , checkin: " + checkin
                + ", checkout: " + checkout + ", rate: " + rate.toString() + ", adults: " + adults.toString() + ", kids: " + kids.toString()
                + ", ccnum: " + ccnum.toString();
    }
}
