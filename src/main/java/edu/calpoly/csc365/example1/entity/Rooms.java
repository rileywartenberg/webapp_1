package edu.calpoly.csc365.example1.entity;

public class Rooms {
    private Integer roomId;
    private String roomName;
    private Integer beds;
    private String bedType;
    private Integer maxOccupancy;
    private Double basePrice;
    private String decor;

    public Rooms() {
        this.roomId = null;
        this.roomName = null;
        this.beds = null;
        this.bedType = null;
        this.maxOccupancy = null;
        this.basePrice = null;
        this.decor = null;
    }

    public Rooms(Integer roomId, String roomName, Integer beds, String bedType, Integer maxOccupancy,
                Double basePrice, String decor) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.beds = beds;
        this.bedType = bedType;
        this.maxOccupancy = maxOccupancy;
        this.basePrice = basePrice;
        this.decor = decor;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public Double getBasePrice() { return basePrice; }

    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public String getDecor() {
        return decor;
    }

    public void setDecor(String decor) {
        this.decor = decor;
    }

    public String toString(){
        return "roomId: " + roomId.toString() + ", roomName: " + roomName + ", beds: " + beds.toString()
                + ", bedType" + bedType + ", maxOccupancy: " + maxOccupancy.toString() + ", basePrice: "
                + basePrice.toString() + ", decor: " + decor;
    }
}