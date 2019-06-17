package com.expenses.breakexpenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpensesDialog extends AppCompatDialogFragment {


   public static EditText expense_name,expense_amount;
    public static CheckBox custom_divide;

    Button done;
   // DatabaseReference databaseReference;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_expenses_dialog,null);

        expense_name = view.findViewById(R.id.expense_name);
        expense_amount= view.findViewById(R.id.amount);
        custom_divide = view.findViewById(R.id.custom_divide);
        done= view.findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Expense expense = new Expense(expense_name.getText().toString().trim(),expense_amount.getText().toString().trim(),);
                Toast.makeText(getContext(), ""+expense_name.getText().toString()+custom_divide.isChecked(), Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


                /*.setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //databaseReference = FirebaseDatabase.getInstance().getReference("notice");
                        Expense expense = new Expense(expense_name.getText().toString().trim(),expense_amount.getText().toString().trim(),""+custom_divide.isChecked());
                        //String id = databaseReference.push().getKey();
                        //databaseReference.child(id).setValue(expense);
                        Toast.makeText(getContext(), ""+expense_name.getText().toString()+custom_divide.isChecked(), Toast.LENGTH_SHORT).show();

                    }
                });*/




        return builder.create();
    }
}
