package com.expenses.breakexpenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendExpenseAdapter extends ArrayAdapter<FriendExpense> {

    private Activity context;
    private List<FriendExpense> friendExpenseList;

    public FriendExpenseAdapter(Activity context, List<FriendExpense> friendExpenseList)
    {
        super(context, R.layout.activity_friend_fragment,friendExpenseList);
        this.context=context;
        this.friendExpenseList=friendExpenseList;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.friends_expense_list,null,true);

        TextView expense_name = (TextView) listViewItem.findViewById(R.id.expense_name);
        TextView expense_date = (TextView) listViewItem.findViewById(R.id.expense_date);
        TextView expense_time =(TextView) listViewItem.findViewById(R.id.expense_time);
        TextView total_amount = (TextView) listViewItem.findViewById(R.id.total_amount);
        TextView paid_till_now = (TextView) listViewItem.findViewById(R.id.paid_till_now);
        TextView due = (TextView) listViewItem.findViewById(R.id.due);
        TextView id =(TextView) listViewItem.findViewById(R.id.expense_id);

        FriendExpense expense = friendExpenseList.get(position);

        expense_name.setText(expense.getExpense_name());
        expense_date.setText(expense.getDate());
        expense_time.setText(expense.getTime());
        total_amount.setText(expense.getExpense_amount());
        paid_till_now.setText(expense.getPaid());
        id.setText(expense.getId());
        due.setText(expense.getDue());


        return listViewItem;
    }
}
