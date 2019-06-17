package com.expenses.breakexpenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TripExpensesAdapter extends ArrayAdapter<Expense> {

    private Activity context;
    private List<Expense> expenseList;

    public TripExpensesAdapter(Activity context, List<Expense> expenseList)
    {
        super(context, R.layout.activity_trip_modification,expenseList);
        this.context=context;
        this.expenseList=expenseList;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.trip_details,null,true);

        TextView list_expense_name = (TextView) listViewItem.findViewById(R.id.list_expense_name);
        TextView list_expense_amount = (TextView) listViewItem.findViewById(R.id.list_expense_amount);
        TextView list_expense_date =(TextView) listViewItem.findViewById(R.id.list_expense_date);
        TextView list_expense_time = (TextView) listViewItem.findViewById(R.id.list_expense_time);
        TextView list_expense_id=(TextView) listViewItem.findViewById(R.id.list_expense_id);

        Expense expense = expenseList.get(position);

        list_expense_name.setText(list_expense_name.getText().toString()+expense.getExpense_name());
        list_expense_amount.setText(list_expense_amount.getText().toString()+expense.getExpense_amount());
        list_expense_date.setText(list_expense_date.getText().toString()+expense.getDate());
        list_expense_time.setText(list_expense_time.getText().toString()+expense.getTime());
        list_expense_id.setText(expense.getId());


        return listViewItem;
    }
}
