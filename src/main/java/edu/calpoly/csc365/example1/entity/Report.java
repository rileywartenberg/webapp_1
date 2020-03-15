package edu.calpoly.csc365.example1.entity;

public class Report {
    private String roomname;
    private double january;
    private double february;
    private double march;
    private double april;
    private double may;
    private double june;
    private double july;
    private double august;
    private double september;
    private double october;
    private double november;
    private double december;
    private double total;


    public Report(String roomname, double january, double february, double march, double april, double may, double june, double july, double august, double september, double october, double november, double december, double total) {
        this.roomname = roomname;
        this.january = january;
        this.february = february;
        this.march = march;
        this.april = april;
        this.may = may;
        this.june = june;
        this.july = july;
        this.august = august;
        this.september = september;
        this.october = october;
        this.november = november;
        this.december = december;
        this.total = total;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setName(String roomname) {
        this.roomname = roomname;
    }

    public Double getJanuary() { return january; }

    public void setJanuary(double january) { this.january = january; }

    public Double getFebruary() {
        return february;
    }

    public void setFebruary(double february) {
        this.february = february;
    }

    public Double getMarch() {
        return march;
    }

    public void setMarch(double march) {
        this.march = march;

    }    public Double getApril() {
        return april;
    }

    public void setApril(double april) {
        this.april = april;
    }    public Double getMay() {
        return may;
    }

    public void setMay(double may) {
        this.may = may;
    }    public Double getJune() {
        return june;
    }

    public void setJune(double june) {
        this.june = june;
    }    public Double getJuly() {
        return july;
    }

    public void setJuly(double july) {
        this.july = july;
    }    public Double getAugust() {
        return august;
    }

    public void setAugust(double august) {
        this.august = august;
    }    public Double getSeptember() {
        return september;
    }

    public void setSeptember(double september) {
        this.september = september;
    }    public Double getOctober() {
        return october;
    }

    public void setOctober(double october) {
        this.october = october;
    }    public Double getNovember() {
        return november;
    }

    public void setNovember(double november) {
        this.november = november;
    }    public Double getDecember() {
        return december;
    }

    public void setDecember(double december) {
        this.december = december;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.december = total;
    }

}