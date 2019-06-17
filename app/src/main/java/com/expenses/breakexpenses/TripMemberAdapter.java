package com.expenses.breakexpenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TripMemberAdapter extends ArrayAdapter<Member> {

    private Activity context;
    private List<Member> memberList;

    public TripMemberAdapter(Activity context, List<Member> membersList)
    {
        super(context, R.layout.activity_trip_modification,membersList);
        this.context=context;
        this.memberList=membersList;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.trip_member_list,null,true);

        TextView trip_member_name = (TextView) listViewItem.findViewById(R.id.member_name);
        TextView trip_member_phone = (TextView) listViewItem.findViewById(R.id.member_phone);
        TextView to_be_paid =(TextView) listViewItem.findViewById(R.id.member_to_be_paid);
        TextView paid = (TextView) listViewItem.findViewById(R.id.member_paid);
        TextView member_id=(TextView) listViewItem.findViewById(R.id.member_id);
        TextView parent_id = (TextView) listViewItem.findViewById(R.id.parent_id);

        Member member = memberList.get(position);

        trip_member_name.setText(trip_member_name.getText().toString()+member.getMember_name());
        trip_member_phone.setText(trip_member_phone.getText().toString()+member.getMember_phone_number());
        to_be_paid.setText(to_be_paid.getText().toString()+member.getTo_be_paid());
        member_id.setText(member.getId());
        paid.setText(paid.getText().toString()+member.getPaid());
        parent_id.setText(""+member.getMember_parent_id());


        return listViewItem;
    }
}
