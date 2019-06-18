package com.expenses.breakexpenses;

public class Friend {

    String name , phone_number,total_amount,total_paid;


    public String getName() {
        return name;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(String total_paid) {
        this.total_paid = total_paid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Friend() {
    }

    public Friend(String name, String phone_number, String total_amount, String total_paid) {
        this.name = name;
        this.phone_number = phone_number;
        this.total_amount = total_amount;
        this.total_paid = total_paid;
    }
}
