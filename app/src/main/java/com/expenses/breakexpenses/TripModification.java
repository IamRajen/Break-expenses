package com.expenses.breakexpenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class TripModification extends AppCompatActivity {


    DatabaseReference databaseReference,databaseReference1;
    DatabaseReference databaseReference2;
    String trip_id ;
    TextView trip_name,created_time,created_by,members;

    Dialog expense_dialog;
    EditText expense_name,expense_amount;
    CheckBox custom_divide;
    Button done,cancel;

    TextView total_amount,updating_amount,member_name,select_date,select_time,to_be_paid_equally;
    EditText to_be_paid,amount_paid;
    Button cancel_submit,next;
    ListView member_list_details,expense_list;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    List<Member> memberList;
    List<Expense> expenseList;

    LinearLayout linearLayout1,linearLayout2;


   // List<String> friend_array;

    List<Member> expense_memberList;


    int i=0;

    Integer divided_amount;
    String final_date,final_time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_modification);



        trip_id = getIntent().getStringExtra("trip_id");
        databaseReference = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_member");

        databaseReference2= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses");

        trip_name = findViewById(R.id.trip_name);
        created_time = findViewById(R.id.created);
        created_by = findViewById(R.id.created_by);
        member_list_details = findViewById(R.id.members_list_details);
        expense_list= findViewById(R.id.trip_details_list);
        expense_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String expense_id= ((TextView)view.findViewById(R.id.list_expense_id)).getText().toString().trim();
                Intent intent = new Intent(TripModification.this,ExpenseModification.class);
                intent.putExtra("expense_id",expense_id);
                intent.putExtra("trip_id",trip_id);
                startActivity(intent);
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


        //friend_array = new ArrayList<>();
        memberList = new ArrayList<>();
        expenseList = new ArrayList<>();
        expense_memberList = new ArrayList<>();

        expense_dialog = new Dialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trip_name.setText("Trip_name: ");
                created_time.setText("Created: ");
                created_by.setText("Created By: ");
                Trips trips = dataSnapshot.getValue(Trips.class);
                trip_name.setText(trip_name.getText().toString()+trips.getTrip_name());
                created_time.setText(created_time.getText().toString()+trips.getDate());
                created_by.setText(created_by.getText().toString()+trips.getTrip_organiser_name());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                memberList.clear();
                for (DataSnapshot dataSnapshot11 : dataSnapshot.getChildren())
                {
                    Member member = dataSnapshot11.getValue(Member.class);
                    memberList.add(member);

                    //Toast.makeText(TripModification.this, ""+member.getMember_name()+member.getMember_phone_number(), Toast.LENGTH_SHORT).show();
                }
                TripMemberAdapter tripMemberAdapter = new TripMemberAdapter(TripModification.this,memberList);
                member_list_details.setAdapter(tripMemberAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Expense expense = dataSnapshot1.getValue(Expense.class);
                    expenseList.add(expense);
                }
                TripExpensesAdapter tripExpensesAdapter= new TripExpensesAdapter(TripModification.this,expenseList);
                expense_list.setAdapter(tripExpensesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //expense_dialog.dismiss();

    }

    public void addExpenses(View view) {

        i=0;

        expense_dialog.setContentView(R.layout.add_expenses_dialog);

        select_date = expense_dialog.findViewById(R.id.expense_date);
        select_time= expense_dialog.findViewById(R.id.expense_time);
        done = expense_dialog.findViewById(R.id.done);
        expense_name = expense_dialog.findViewById(R.id.expense_name);
        expense_amount= expense_dialog.findViewById(R.id.amount);
        custom_divide = expense_dialog.findViewById(R.id.custom_divide);
        cancel = expense_dialog.findViewById(R.id.cancel);

        linearLayout1 = expense_dialog.findViewById(R.id.hidden_done_button1);
        linearLayout2 = expense_dialog.findViewById(R.id.hidden_done_button2);



        total_amount = expense_dialog.findViewById(R.id.total_amount);
        updating_amount = expense_dialog.findViewById(R.id.updating_amount);
        member_name = expense_dialog.findViewById(R.id.member_name);
        to_be_paid = expense_dialog.findViewById(R.id.to_be_paid);
        to_be_paid_equally = expense_dialog.findViewById(R.id.to_be_paid_equally);
        amount_paid= expense_dialog.findViewById(R.id.amount_paid);
        cancel_submit = expense_dialog.findViewById(R.id.cancel_submit);
        next= expense_dialog.findViewById(R.id.next);




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_dialog.dismiss();
            }
        });

        cancel_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_dialog.dismiss();
            }
        });


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month= calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog= new DatePickerDialog(expense_dialog.getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
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

                TimePickerDialog timePickerDialog=new TimePickerDialog(expense_dialog.getContext(),timeSetListener,hour,minute,true);

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







        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                String expensename = expense_name.getText().toString().trim();
                String expenseamount = expense_amount.getText().toString().trim();
                String customdivide = custom_divide.isChecked()+"";
                if (expensename.equals(""))
                {
                    expense_name.setError("please fill the field");
                    expense_name.requestFocus();
                }
                if(expenseamount.equals(""))
                {
                    expense_amount.setError("please fill the field");
                    expense_amount.requestFocus();
                }
                else {

                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses");
                    String expense_id =databaseReference2.push().getKey();
                    Expense expense = new Expense(expense_name.getText().toString().trim(),expense_amount.getText().toString().trim(),expense_id,final_date,final_time);
                    databaseReference2.child(expense_id).setValue(expense);

                    push_member(expense_id);

                    if(customdivide.equals("true"))
                    {
                        linearLayout1.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);

                        total_amount.setText(expenseamount);
                        updating_amount.setText(expenseamount);
                        Member member = expense_memberList.get(i);
                        member_name.setText(member.getMember_name()+"\n"+member.getMember_phone_number());
                        //to_be_paid.setFocusable(true);
                        submit_data(expense_id);

                    }
                    else if(customdivide.equals("false"))
                    {
                        linearLayout1.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);

                        divided_amount= Integer.parseInt(expenseamount)/memberList.size();
                        to_be_paid_equally.setText(divided_amount.toString());
                        total_amount.setText(expenseamount);
                        updating_amount.setText(expenseamount);
                        Member member = expense_memberList.get(i);
                        member_name.setText(member.getMember_name()+"\n"+member.getMember_phone_number());
                        to_be_paid.setVisibility(View.GONE);
                        to_be_paid_equally.setVisibility(View.VISIBLE);
                        submit_data(expense_id);
                    }
                    //Toast.makeText(getApplicationContext(), "" + expense_amount.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        expense_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        expense_dialog.show();





    }

    private void push_member(String expense_id) {


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses").child(expense_id).child("expense_members");
        for (int i=0;i<memberList.size();i++)
        {
            String member_id= databaseReference.push().getKey();
            Member member = memberList.get(i);
            Member expense_member=new Member(member_id,member.getMember_name(),member.getMember_phone_number(),"0","0",member.getId());
            databaseReference.child(member_id).setValue(expense_member);
            expense_memberList.add(expense_member);
        }

    }

    private void submit_data(final String expense_id) {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (next.getText().equals("Submit"))
                {
                    Integer t;
                    if (custom_divide.isChecked())
                         t= Integer.parseInt(to_be_paid.getText().toString());
                    else
                        t= Integer.parseInt(to_be_paid_equally.getText().toString());
                    Integer p = Integer.parseInt(amount_paid.getText().toString());

                    Member member = expense_memberList.get(i);
                    if(custom_divide.isChecked())
                        member.setTo_be_paid(to_be_paid.getText().toString().trim());
                    else
                        member.setTo_be_paid(to_be_paid_equally.getText().toString().trim());
                    member.setPaid(amount_paid.getText().toString().trim());
                    expense_memberList.remove(i);
                    expense_memberList.add(i,member);
                    Toast.makeText(TripModification.this, "Expenses Added", Toast.LENGTH_SHORT).show();
                    expense_dialog.dismiss();
                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses").child(expense_id).child("expense_members");
                    //DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_e").child(expense_id)
                    for (int k=0;k<expense_memberList.size();k++)
                    {
                        Member member1= expense_memberList.get(k);
                        databaseReference2.child(member1.getId()).setValue(member1);
                        Member member2 = memberList.get(k);
                        member2.setTo_be_paid((Integer.parseInt(member2.getTo_be_paid())+Integer.parseInt(member1.getTo_be_paid()))+"");
                        member2.setPaid(((Integer.parseInt(member1.getPaid()))+(Integer.parseInt(member2.getPaid())))+"");
                        memberList.remove(k);
                        memberList.add(k,member2);
                        databaseReference1.child(member2.getId()).setValue(member2);
                        //Member parent_member = databaseReference1.child(member1.getMember_parent_id());
                    }
                    expense_memberList.clear();
                    onStart();






                }
                else if(next.getText().equals("Next")){

                    if(to_be_paid.getText().toString().equals("") && custom_divide.isChecked())
                    {
                        to_be_paid.setError("Please fill the field..\nif nothing fill it with 0");
                        to_be_paid.requestFocus();
                    }
                    else if(amount_paid.getText().toString().equals(""))
                    {
                        amount_paid.setError("Please fill the field..\nif nothing fill it with 0");
                        amount_paid.requestFocus();
                    }
                    else {
                        Integer t;
                        if (custom_divide.isChecked())
                            t = Integer.parseInt(to_be_paid.getText().toString());
                        else
                            t = Integer.parseInt(to_be_paid_equally.getText().toString());
                        Integer p = Integer.parseInt(amount_paid.getText().toString());

                        Member member = expense_memberList.get(i);
                        if (custom_divide.isChecked())
                            member.setTo_be_paid(to_be_paid.getText().toString().trim());
                        else
                            member.setTo_be_paid(to_be_paid_equally.getText().toString().trim());
                        member.setPaid(amount_paid.getText().toString().trim());
                        expense_memberList.remove(i);
                        expense_memberList.add(i,member);


                        i++;
                        updating_amount.setText("" + (Integer.parseInt(updating_amount.getText().toString()) - t));
                        if(i<expense_memberList.size()) {
                            Member member1 = expense_memberList.get(i);
                            member_name.setText(member1.getMember_name() + "\n" + member1.getMember_phone_number());
                        }
                        if(custom_divide.isChecked()) {
                            to_be_paid.setText("");
                            amount_paid.setText("");
                            to_be_paid.requestFocus();
                        }else {
                            amount_paid.setText("");
                            amount_paid.requestFocus();
                        }


                        if (i == expense_memberList.size()-1) {
                            next.setText("Submit");
                        }
                    }



                }

            }
        });

    }

    public void delete_trip(View view) {

        databaseReference.removeValue();
        Intent intent=new Intent(this,HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
