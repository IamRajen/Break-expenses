package com.expenses.breakexpenses;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {

    View rootView;
    FloatingActionButton fab;

    Dialog dialog;

    ListView trip_list;
    List<Trips> trips_arrayList;
    DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_trip_fragment, container, false);

       /* listView = rootView.findViewById(R.id.notice_list);
        notices = new ArrayList<>();
*/
       trips_arrayList = new ArrayList<>();
       trip_list = rootView.findViewById(R.id.trip_list);





        databaseReference = FirebaseDatabase.getInstance().getReference("trip_root");

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "on Trip", Toast.LENGTH_SHORT).show();
                //showPopUp();
                startActivity(new Intent(getContext(),PlanATrip.class));
            }
        });
        //Toast.makeText(getContext(), ""+Homepage.sharedPreferences.getString("name",""), Toast.LENGTH_SHORT).show();


        trip_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.trip_id);
                //Toast.makeText(getContext(), ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                DatabaseReference databaseReference2= databaseReference.child(textView.getText().toString().trim());
//                databaseReference2.removeValue();

                Intent intent = new Intent(getContext(),TripModification.class);
                intent.putExtra("trip_id",textView.getText().toString());
                trips_arrayList.clear();
                startActivity(intent);

            }
        });



        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {


            int i=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trips_arrayList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Trips trips = dataSnapshot1.getValue(Trips.class);
                    if (trips.getTrip_organiser_phone().equals(HomePage.sharedPreferences.getString("user_phone","")))
                     trips_arrayList.add(trips);
                }

                TripAdapter tripAdapter = new TripAdapter(getActivity(),trips_arrayList);
                trip_list.setAdapter(tripAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });
    }
}
