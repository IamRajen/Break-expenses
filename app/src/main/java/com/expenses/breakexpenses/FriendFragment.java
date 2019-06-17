package com.expenses.breakexpenses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendFragment extends android.support.v4.app.Fragment {


    View rootView;
    FloatingActionButton floatingActionButton;
    TextView textView;

    ListView listView;

    DatabaseReference databaseReference ,databaseReference1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_friend_fragment,container,false);
        floatingActionButton = rootView.findViewById(R.id.fab);


        listView = rootView.findViewById(R.id.trip_list);


        databaseReference = FirebaseDatabase.getInstance().getReference("trip_root");

        //Toast.makeText(getContext(), ""+databaseReference, Toast.LENGTH_SHORT).show();
        //textView = rootView.findViewById(R.id.data);





        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "On Friends Fragment ", Toast.LENGTH_SHORT).show();
            }
        });

        //Toast.makeText(getContext(), ""+Homepage.sharedPreferences.getString("userphone",""), Toast.LENGTH_SHORT).show();
       // textView.setText(textView.getText().toString()+Homepage.sharedPreferences.getString("userphone",""));



        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Trips trips = dataSnapshot1.getValue(Trips.class);
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("trip_root").child(trips.getId()).child("trip_member");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot11 : dataSnapshot.getChildren())
                            {
                                Member member = dataSnapshot11.getValue(Member.class);
                                //Toast.makeText(getContext(), ""+member.getMember_name()+member.getMember_phone_number(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(getContext(), ""+dataSnapshot1.getValue(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), trips.getMember_name()+"\n"+member.getMember_phone_number(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
