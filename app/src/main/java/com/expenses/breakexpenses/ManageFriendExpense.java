package com.expenses.breakexpenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class ManageFriendExpense extends AppCompatActivity {

    String friend_id;
    DatabaseReference databaseReference,databaseReference1;
    List<FriendExpense> friend_expenseList;
    List<Friend> friendList;

    ListView listView;

    Dialog dialog,dialog1;

    TextView name,mobile_number,total_amount,paid_till_now,due;
    Button add_expense;
    private String final_time,final_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friend_expense);

        dialog= new Dialog(this);
        dialog1 = new Dialog(this);

        friend_id= getIntent().getStringExtra("friend_id");
        name = findViewById(R.id.name);
        mobile_number= findViewById(R.id.mobile_number);
        total_amount= findViewById(R.id.total_amount);
        paid_till_now= findViewById(R.id.paid_till_now);
        due = findViewById(R.id.due);
        add_expense = findViewById(R.id.add_expenses);
        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenses();
            }
        });

         listView= findViewById(R.id.friend_expense_list);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 openDialog(view);
             }
         });



        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        final_time=hour+":"+minute;
        final_date=day+"/"+(month+1)+"/"+year;

        SharedPreferences sharedPreferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference("friends_root").child(sharedPreferences.getString("user_phone","")).child("friend_list").child(friend_id);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("friends_root").child(sharedPreferences.getString("user_phone","")).child("friend_list").child(friend_id).child("expense");
        friend_expenseList= new ArrayList<>();
        friendList = new ArrayList<>();


    }

    private void openDialog(View view) {

        dialog1.setContentView(R.layout.friend_expense_dialog);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();

        final TextView expense_name = dialog1.findViewById(R.id.expense_name);
        final TextView expense_date = dialog1.findViewById(R.id.expense_date);
        final TextView expense_time = dialog1.findViewById(R.id.expense_time);
        final TextView expense_amount = dialog1.findViewById(R.id.total_amount);
        final TextView amount_paid_till_now = dialog1.findViewById(R.id.paid_till_now);
        final TextView amount_due = dialog1.findViewById(R.id.due);
        final EditText paying_now = dialog1.findViewById(R.id.paying_now);

        Button cancel = dialog1.findViewById(R.id.cancel);
        Button update = dialog1.findViewById(R.id.update);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        TextView textView= (TextView) view.findViewById(R.id.expense_id);
        final DatabaseReference databaseReference2= databaseReference1.child(textView.getText().toString());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FriendExpense friendExpense = dataSnapshot.getValue(FriendExpense.class);
                expense_name.setText(friendExpense.getExpense_name());
                expense_date.setText(friendExpense.getDate());
                expense_time.setText(friendExpense.getTime());
                expense_amount.setText(friendExpense.getExpense_amount());
                amount_paid_till_now.setText(friendExpense.getPaid());
                amount_due.setText(((Integer.parseInt(friendExpense.getExpense_amount()))-(Integer.parseInt(friendExpense.getPaid())))+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paying_now.getText().toString().equals(""))
                {
                    paying_now.setError("Please Add some Amount");
                    paying_now.requestFocus();
                }
                else {
                    String total_paid= (Integer.parseInt(amount_paid_till_now.getText().toString())+Integer.parseInt(paying_now.getText().toString()))+"";
                    databaseReference2.child("paid").setValue(total_paid);
                    dialog1.dismiss();
                }
            }
        });


    }

    private void addExpenses() {


        dialog.setContentView(R.layout.add_friend_expense_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final EditText expense = (EditText)dialog.findViewById(R.id.expense_name);
        final EditText expense_amount = (EditText)dialog.findViewById(R.id.amount);
        final TextView select_date = dialog.findViewById(R.id.expense_date);
        final TextView select_time = dialog.findViewById(R.id.expense_time);
        final EditText paid = dialog.findViewById(R.id.paid);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button done = dialog.findViewById(R.id.done);


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month= calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog= new DatePickerDialog(dialog.getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                Calendar calendar= Calendar.getInstance();
                select_date.setText(dayOfMonth+"/"+month+"/"+year);
                final_date=dayOfMonth+"/"+month+"/"+year;
            }

        };

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(dialog.getContext(),timeSetListener,hour,minute,true);

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                select_time.setText(hourOfDay+":"+minute);
                final_time=hourOfDay+":"+minute;
            }
        };




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("friends_root").child(sharedPreferences.getString("user_phone","")).child("friend_list").child(friend_id).child("expense");

                String name = expense.getText().toString().trim();
                 String amount = expense_amount.getText().toString().trim();
                 String paid_now = paid.getText().toString().trim();
                String id = databaseReference1.push().getKey();
                FriendExpense friendExpense = new FriendExpense(id,name,amount,final_date,final_time,paid_now);

                databaseReference1.child(id).setValue(friendExpense);


                //Toast.makeText(ManageFriendExpense.this, ""+friendList.get(0).getTotal_amount()+friendList.get(0).getTotal_paid(), Toast.LENGTH_SHORT).show();
                String total_amount = friendList.get(0).getTotal_amount();
                String total_paid = friendList.get(0).getTotal_paid();
                total_amount = (Integer.parseInt(total_amount)+Integer.parseInt(amount))+"";
                total_paid =(Integer.parseInt(total_paid)+Integer.parseInt(paid_now))+"";
                databaseReference.child("total_amount").setValue(total_amount);
                databaseReference.child("total_paid").setValue(total_paid);
                friendList.clear();
                onStart();


                dialog.dismiss();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendList.clear();
                Friend friend = dataSnapshot.getValue(Friend.class);
                friendList.add(friend);
                name.setText(friend.getName());
                mobile_number.setText(friend.getPhone_number());
                total_amount.setText(friend.getTotal_amount());
                paid_till_now.setText(friend.getTotal_paid());
                due.setText((Integer.parseInt(friend.getTotal_amount())-Integer.parseInt(friend.getTotal_paid()))+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friend_expenseList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    FriendExpense friendExpense = dataSnapshot1.getValue(FriendExpense.class);
                    friendExpense.setDue((Integer.parseInt(friendExpense.getExpense_amount())-Integer.parseInt(friendExpense.getPaid()))+"");
                    friend_expenseList.add(friendExpense);
                }
                FriendExpenseAdapter friendExpenseAdapter= new FriendExpenseAdapter(ManageFriendExpense.this,friend_expenseList);
                listView.setAdapter(friendExpenseAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
