package com.expenses.breakexpenses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact>{

    private Context mContext;
    private ArrayList<Contact> contactList = new ArrayList<>();

    public ContactAdapter(@NonNull Context context,  ArrayList<Contact> list) {
        super(context, 0 , list);
        mContext = context;
        contactList = list;
    }



}
