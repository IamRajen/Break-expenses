package com.expenses.breakexpenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TripAdapter extends ArrayAdapter<Trips> {

    private Activity context;
    private List<Trips> tripsList;

    public TripAdapter(Activity context, List<Trips> tripsList)
    {
        super(context, R.layout.activity_trip_fragment,tripsList);
        this.context=context;
        this.tripsList=tripsList;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.trip_list,null,true);

        TextView trip_name = (TextView) listViewItem.findViewById(R.id.trip_name);
        TextView created = (TextView) listViewItem.findViewById(R.id.created);
        TextView created_by =(TextView) listViewItem.findViewById(R.id.created_by);
        TextView trip_id = (TextView) listViewItem.findViewById(R.id.trip_id);

        Trips trips = tripsList.get(position);

        trip_name.setText(trip_name.getText().toString()+trips.getTrip_name());
        created.setText(created.getText().toString()+trips.getDate());
        created_by.setText(created_by.getText().toString()+trips.getTrip_organiser_name());
        trip_id.setText(trips.getId());


        return listViewItem;
    }
}
