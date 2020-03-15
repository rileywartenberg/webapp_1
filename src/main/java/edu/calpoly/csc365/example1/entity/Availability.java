package edu.calpoly.csc365.example1.entity;

import java.sql.Date;

public class Availability {

    private Date day;
    private String roomCode;
    private String roomName;
    private Double popularity;
    private Double price;
    private String available;
    private Date nextDate;
    private Integer length;
    private String bedType;
    private Integer beds;
    private Integer maxOccupancy;

    public Availability() {
        this.roomCode = null;
        this.day = null;
        this.roomName = null;
        this.popularity = null;
        this.price = null;
        this.available = null;
        this.nextDate = null;
        this.length = null;
        this.bedType = null;
        this.beds = null;
        this.maxOccupancy = null;
    }

    public Availability(Date day, String roomName, Double popularity, Double price, String available,
                        Date nextDate, Integer length, Integer beds, String bedType, Integer maxOccupancy, String roomCode) {
        this.roomCode = roomCode;
        this.day = day;
        this.roomName = roomName;
        this.popularity = popularity;
        this.price = price;
        this.available = available;
        this.nextDate = nextDate;
        this.length = length;
        this.bedType = bedType;
        this.beds = beds;
        this.maxOccupancy = maxOccupancy;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate= nextDate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }


    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }


    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }


    public String toString(){
        return ", roomName: " + roomName + ", beds: " + beds.toString()
                + ", bedType" + bedType + ", maxOccupancy: " + maxOccupancy.toString();
    }
}

