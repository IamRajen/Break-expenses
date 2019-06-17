package com.expenses.breakexpenses;

public class Expense {

    String expense_name,expense_amount,id,date,time;

    public Expense(String expense_name, String expense_amount, String id, String date, String time) {
        this.expense_name = expense_name;
        this.expense_amount = expense_amount;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Expense() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
