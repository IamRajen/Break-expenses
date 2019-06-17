package com.expenses.breakexpenses;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExpenseModification extends AppCompatActivity {


    Dialog modify_dialog;
    DatabaseReference databaseReference,databaseReference1,databaseReference2;

    String trip_id,expense_id;


    TextView total_to_be_paid,member_name,due;
    EditText wanna_paid;
    Button paid,update;
    ImageView close;

    ListView listView;
    List<Member> memberList;
    List<Member> parentMemberList;


    String s1;



    TextView expense_name,expense_amount,expense_date,expense_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_modification);


        expense_name=findViewById(R.id.expense_name);
        expense_amount= findViewById(R.id.expense_amount);
        expense_date= findViewById(R.id.expense_date);
        expense_time= findViewById(R.id.expense_time);
        listView=(ListView) findViewById(R.id.expense_member_list);
        memberList = new ArrayList<>();
        parentMemberList= new ArrayList<>();
        modify_dialog=new Dialog(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openModifyDialog(view);
            }
        });



        trip_id= getIntent().getStringExtra("trip_id");
        expense_id= getIntent().getStringExtra("expense_id");
        databaseReference = FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses").child(expense_id);
        databaseReference1= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_expenses").child(expense_id).child("expense_members");
        databaseReference2= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_member");
        //Expense expense = databaseReference
    }

    private void openModifyDialog(final View view) {


        TextView list_member_id=(TextView)view.findViewById(R.id.member_id);
        TextView list_member_name=(TextView)view.findViewById(R.id.member_name);
        TextView list_member_parent_id=(TextView)view.findViewById(R.id.parent_id);
        TextView list_member_phone=(TextView)view.findViewById(R.id.member_phone);
        TextView list_member_to_be_paid=(TextView)view.findViewById(R.id.member_to_be_paid);
        TextView list_member_paid=(TextView)view.findViewById(R.id.member_paid);

        final Member member= new Member(list_member_id.getText().toString(),list_member_name.getText().toString(),list_member_phone.getText().toString(),list_member_to_be_paid.getText().toString(),list_member_paid.getText().toString(),list_member_parent_id.getText().toString());









        modify_dialog.setContentView(R.layout.modify_expense_data);

        close = modify_dialog.findViewById(R.id.close);
        total_to_be_paid=modify_dialog.findViewById(R.id.total_to_be_paid);
        wanna_paid= modify_dialog.findViewById(R.id.wanna_paid);
        paid= modify_dialog.findViewById(R.id.paid);
        update= modify_dialog.findViewById(R.id.update);
        member_name=modify_dialog.findViewById(R.id.member_name);
        due=modify_dialog.findViewById(R.id.due);




        member_name.setText(((TextView)view.findViewById(R.id.member_name)).getText().toString()+"\n"+((TextView)view.findViewById(R.id.member_phone)).getText().toString());
        total_to_be_paid.setText(((TextView)view.findViewById(R.id.member_to_be_paid)).getText().toString());
        int  x=Integer.parseInt(((TextView)view.findViewById(R.id.member_to_be_paid)).getText().toString());
        int  y=Integer.parseInt(((TextView)view.findViewById(R.id.member_paid)).getText().toString());
        due.setText((x-y)+"");



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify_dialog.dismiss();
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paid_all_dues(member);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wanna_paid.getText().toString().equals(""))
                {
                    wanna_paid.setError("Please fill the field..\nif nothing fill it with 0");
                    wanna_paid.requestFocus();
                }
                else
                {
                    update_data(member,wanna_paid.getText().toString().trim());
                }
            }
        });
        modify_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modify_dialog.show();


    }

    private void update_data(final Member member,  String paid) {



        member.setPaid(((Integer.parseInt(member.getPaid()))+(Integer.parseInt(paid)))+"");
        databaseReference1.child(member.getId()).setValue(member);


        for (int i=0;i<parentMemberList.size();i++)
        {
            Member member1=parentMemberList.get(i);
            if (member1.getId().equals(member.getMember_parent_id()))
            {
                int x= Integer.parseInt(member1.getPaid());
                int y= Integer.parseInt(paid);
                x=x+y;
                String total_paid= x+"";
                member1.setPaid(total_paid);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_member").child(member1.getId());
                databaseReference.setValue(member1);
                break;
            }
        }

        modify_dialog.dismiss();


    }

    private void paid_all_dues(Member member) {


        int due=(Integer.parseInt(member.getTo_be_paid()))-(Integer.parseInt(member.getPaid()));

        member.setPaid(member.getTo_be_paid());
        databaseReference1.child(member.getId()).setValue(member);
        for (int i=0;i<parentMemberList.size();i++)
        {
            Member member1=parentMemberList.get(i);
            if (member1.getId().equals(member.getMember_parent_id()))
            {
                int x= Integer.parseInt(member1.getPaid());
                int y=due;
                x=x+y;
                String total_paid= x+"";
                member1.setPaid(total_paid);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("trip_root").child(trip_id).child("trip_member").child(member1.getId());
                databaseReference.setValue(member1);
                break;
            }
        }
        modify_dialog.dismiss();


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Expense expense= dataSnapshot.getValue(Expense.class);
                expense_name.setText("For : ");
                expense_date.setText("Date : ");
                expense_amount.setText("Amount : ");
                expense_time.setText("Time : ");
                expense_name.setText(expense_name.getText().toString()+expense.getExpense_name());
                expense_date.setText(expense_date.getText().toString()+expense.getDate());
                expense_amount.setText(expense_amount.getText().toString()+expense.getExpense_amount());
                expense_time.setText(expense_time.getText().toString()+expense.getTime());


                //Toast.makeText(ExpenseModification.this, ""+expense.getExpense_name(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                memberList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Member member = dataSnapshot1.getValue(Member.class);
                    memberList.add(member);
                    //Toast.makeText(ExpenseModification.this, ""+member.getMember_parent_id()+"\n"+member.getId()+"\n"+member.getMember_name()+"\n"+member.getMember_phone_number()+"\n"+member.getPaid()+"\n"+member.getTo_be_paid(), Toast.LENGTH_SHORT).show();


                }
                TripMemberAdapter tripExpenseMemberAdapter = new TripMemberAdapter(ExpenseModification.this,memberList);
                //Toast.makeText(ExpenseModification.this, ""+tripExpenseMemberAdapter.getItem(1), Toast.LENGTH_SHORT).show();
                listView.setAdapter(tripExpenseMemberAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parentMemberList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Member member1= dataSnapshot1.getValue(Member.class);
                    parentMemberList.add(member1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
