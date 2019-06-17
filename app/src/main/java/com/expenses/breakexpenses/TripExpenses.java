package com.expenses.breakexpenses;

import java.util.List;

public class TripExpenses {


    String id,expense_name,expense_amount;

    public TripExpenses(String id,String expense_name, String expense_amount, List<Member> memberList) {
        this.expense_name = expense_name;
        this.expense_amount = expense_amount;
        this.id = id;
    }

    public TripExpenses() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
