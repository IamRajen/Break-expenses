package com.expenses.breakexpenses;

public class FriendExpense {

    String id,expense_name,expense_amount,date,time,paid,due;

    public String getPaid() {
        return paid;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    public String getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(String expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public FriendExpense() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FriendExpense(String id, String expense_name, String expense_amount, String date, String time, String paid) {
        this.id = id;
        this.expense_name = expense_name;
        this.expense_amount = expense_amount;
        this.date = date;
        this.time = time;
        this.paid = paid;
    }
}
