package com.expenses.breakexpenses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlanATrip extends AppCompatActivity {


   // private static final String TAG = PlanATrip.class.getSimpleName();

    private static final int PERMISSION_REQUEST_READ_CONTACT = 100;
    Cursor cursor;


    DatabaseReference databaseReference;


    private ArrayAdapter<String> adapter,contact_adapter;
   // private ContactAdapter contact_adaptor;
    private ArrayList<String> arrayList,contact_list;

    private ArrayList<String> contacts;



    int number_of_members,counter;

    EditText trip_name,member_details;
    Button submit,create_trip;
    ListView members_list;
    TextView textView;
    AutoCompleteTextView autoCompleteTextView;
    ProgressBar progressBar;

    ArrayList<Contact> contacts1;

    List<Member> memberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_atrip);

        trip_name = findViewById(R.id.trip_name);
        trip_name.requestFocus();
        submit = findViewById(R.id.trip_name_submission);
        create_trip = findViewById(R.id.create_trip);
        member_details = findViewById(R.id.members_details);
        textView = findViewById(R.id.members_list_heading);
        autoCompleteTextView = findViewById(R.id.members_names_input);
        members_list = findViewById(R.id.members_list);
        progressBar= findViewById(R.id.progressBar);


        arrayList = new ArrayList<>();
        contact_list= new ArrayList<>();
        contacts = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        contact_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts);
        members_list.setAdapter(adapter);

        contacts1 = new ArrayList<>();



        databaseReference = FirebaseDatabase.getInstance().getReference("trip_root");



        memberList = new ArrayList<>();

    }


    public void submit_data(View view) {

        //Toast.makeText(this, ""+submit.getText().toString(), Toast.LENGTH_SHORT).show();

        if(member_details.getText().toString().trim().equals("") || trip_name.getText().toString().equals(""))
        {
            member_details.setError("please enter number of members you have");
            member_details.requestFocus();
        }
        else if(submit.getText().toString().equals("Submit") )
        {

            //progressBar.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            number_of_members =Integer.parseInt( member_details.getText().toString().trim());
            member_details.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                contacts.add(name+"\n"+phoneNumber);
                contact_list.add(name+"\n"+phoneNumber);
                //contacts1.add(new Contact(name,phoneNumber));

            }
            //contact_adaptor = new ContactAdapter(this,contacts1);

            autoCompleteTextView.setAdapter(contact_adapter);
            phones.close();
            //progressBar.setVisibility(View.GONE);
            autoCompleteTextView.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            submit.setText("Next");
            autoCompleteTextView.setHint((++counter)+".Name");
            autoCompleteTextView.requestFocus();

        }
        else if (submit.getText().toString().trim().equals("Next") && autoCompleteTextView.getText().toString().equals(""))
        {
            autoCompleteTextView.setError("please enter members name");
            autoCompleteTextView.requestFocus();
        }
        else if(submit.getText().toString().trim().equals("Next"))
        {
            int flag=0;
            int flag1=0;
            for (int i=0;i<contact_list.size();i++)
            {
                if (contact_list.get(i).equals(autoCompleteTextView.getText().toString().trim()))
                {
                    flag1=1;
                    break;
                }
            }
            for (String s : arrayList)
            {
                if (s.equals(autoCompleteTextView.getText().toString().trim()))
                {
                    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.setError("Name Already Added..!!");
                    flag=1;
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setHint(counter+". Name");
                    break;
                }
            }
            if(flag!=1 && flag1==1)
            {
                textView.setVisibility(View.VISIBLE);
                String text = autoCompleteTextView.getText().toString();
                //Toast.makeText(this,""+ text.substring(text.lastIndexOf('+')+3), Toast.LENGTH_SHORT).show();
                String phone= "";
                String name ="";
                int cropping_point=text.length()-10;
                if (text.indexOf('+')!=-1)
                {
                    cropping_point=text.indexOf('+')-1;
                    phone =text.substring(text.lastIndexOf('+')-1).trim();
                }
                else {

                    phone=text.substring(text.length()-11,text.length()).trim();
                }
                name = text.substring(0,cropping_point).trim();
                contacts1.add(new Contact(name,phone));
                arrayList.add(autoCompleteTextView.getText().toString());
                adapter.notifyDataSetChanged();
                if (counter==number_of_members)
                {
                    submit.setVisibility(View.GONE);
                    autoCompleteTextView.setVisibility(View.GONE);
                    create_trip.setVisibility(View.VISIBLE);
                }
                autoCompleteTextView.setText("");
                autoCompleteTextView.setHint((++counter)+". Name");
            }



        }

    }

    public void create_trip(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
        String id = databaseReference.push().getKey();
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Trips trips = new Trips(id,trip_name.getText().toString().trim(),sharedPreferences.getString("user_phone",""),sharedPreferences.getString("user_name",""), date,"");
        databaseReference.child(id).setValue(trips);
        DatabaseReference databaseReference1 = databaseReference.child(id).child("trip_member");
        for (int i=0;i<contacts1.size();i++)
        {
            String id1 = databaseReference1.push().getKey();
            Contact contact= contacts1.get(i);
            Member member = new Member(id1,contact.getContact_name(),contact.getContact_phone_number(),"0","0");
            databaseReference1.child(id1).setValue(member);
        }
        Toast.makeText(this, "Trip Created", Toast.LENGTH_SHORT).show();

        Intent intent= new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        onBackPressed();

    }
}
