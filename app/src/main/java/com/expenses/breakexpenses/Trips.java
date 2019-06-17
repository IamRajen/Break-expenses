package com.expenses.breakexpenses;

public class Trips {


    String id ,trip_name,trip_organiser_phone,date,member_names,trip_organiser_name;


    public Trips() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrip_organiser_name() {
        return trip_organiser_name;
    }

    public void setTrip_organiser_name(String trip_organiser_name) {
        this.trip_organiser_name = trip_organiser_name;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_organiser_phone() {
        return trip_organiser_phone;
    }

    public void setTrip_organiser_phone(String trip_organiser) {
        this.trip_organiser_phone = trip_organiser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMember_names() {
        return member_names;
    }

    public void setMember_names(String member_names) {
        this.member_names = member_names;
    }

    public Trips(String id,String trip_name, String trip_organiser_phone,String trip_organiser_name, String date, String member_names) {
        this.trip_name = trip_name;
        this.trip_organiser_phone = trip_organiser_phone;
        this.date = date;
        this.member_names = member_names;
        this.trip_organiser_name= trip_organiser_name;
        this.id= id;
    }
}
