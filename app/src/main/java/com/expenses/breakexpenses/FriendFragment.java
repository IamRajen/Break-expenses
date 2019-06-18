package com.expenses.breakexpenses;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendFragment extends android.support.v4.app.Fragment {


    View rootView;
    FloatingActionButton floatingActionButton;

    private ArrayAdapter<String> contact_adapter;
    Dialog dialog;
    private ArrayList<String> contact_list;

    private ArrayList<String> contacts;

    private ArrayList<Friend> friendArrayList;


    ListView listView;

    DatabaseReference databaseReference ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_friend_fragment,container,false);
        floatingActionButton = rootView.findViewById(R.id.fab);


        listView = rootView.findViewById(R.id.friend_list);
        contact_list= new ArrayList<>();
        contacts = new ArrayList<>();
        contact_adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,contacts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getContext(),ManageFriendExpense.class);
                intent.putExtra("friend_id",((TextView)(view.findViewById(R.id.friend_phone))).getText().toString());
                startActivity(intent);
            }
        });


        friendArrayList= new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("friends_root");




        dialog= new Dialog(getContext());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });



        return rootView;
    }

    private void showDialog() {

        dialog.setContentView(R.layout.add_friend_dialog);

        final AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)dialog.findViewById(R.id.friend_name);
        Button addFriend = (Button)dialog.findViewById(R.id.add);


        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(name+"\n"+phoneNumber);
            contact_list.add(name+"\n"+phoneNumber);

        }

        autoCompleteTextView.setAdapter(contact_adapter);
        phones.close();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();



        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_friend(autoCompleteTextView.getText().toString());

            }
        });


    }

    private void add_friend(String s) {

        String text = s;
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
        Friend friend= new Friend(name,phone,"0","0");
        if (check(friend))
        {
            Toast.makeText(getActivity(), "Friend Already Added", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }
        else
        {
            save_friend(name,phone);
            dialog.dismiss();
            onStart();
        }


    }

    private boolean check(Friend friend) {

        for (Friend friend1:friendArrayList)
        {
            if(friend1.getPhone_number().compareTo(friend.getPhone_number())==0)
            {
                Toast.makeText(getContext(), "Friend Already Added", Toast.LENGTH_SHORT).show();
                return true;

            }
        }
        return false;

    }

    private boolean save_friend(String name,String phone_number ){

        Friend new_friend = new Friend(name,phone_number,"0","0");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        databaseReference.child(sharedPreferences.getString("user_phone","")).child("friend_list").child(phone_number).setValue(new_friend);

        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        DatabaseReference databaseReference1=databaseReference.child(sharedPreferences.getString("user_phone","")).child("friend_list");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Friend friend= dataSnapshot1.getValue(Friend.class);
                    friendArrayList.add(friend);
                }
                if(friendArrayList.size()>0) {
                    FriendAdapter friendAdapter = new FriendAdapter(getActivity(), friendArrayList);
                    listView.setAdapter(friendAdapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
