package com.expenses.breakexpenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {

    private Activity context;
    private List<Friend> friendList;

    public FriendAdapter(Activity context, List<Friend> friendList)
    {
        super(context, R.layout.activity_friend_fragment,friendList);
        this.context=context;
        this.friendList=friendList;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.friends_list,null,true);

        TextView friend_name = (TextView) listViewItem.findViewById(R.id.friend_name);
        TextView friend_phone = (TextView) listViewItem.findViewById(R.id.friend_phone);
        TextView total_amount =(TextView) listViewItem.findViewById(R.id.friend_total_amount);
        TextView paid = (TextView) listViewItem.findViewById(R.id.friend_total_paid);

        Friend friend = friendList.get(position);

        friend_name.setText(friend.getName());
        friend_phone.setText(friend.getPhone_number());
        total_amount.setText(friend.getTotal_amount());
        paid.setText(friend.getTotal_paid());


        return listViewItem;
    }
}
