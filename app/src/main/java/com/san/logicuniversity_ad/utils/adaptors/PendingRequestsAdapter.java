package com.san.logicuniversity_ad.utils.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Request;

import java.util.ArrayList;

public class PendingRequestsAdapter extends ArrayAdapter<Request> {

    private static ArrayList<Request> items;
    private LayoutInflater inflater;

    public PendingRequestsAdapter(Context context, ArrayList<Request> objects) {
        super(context, 0, objects);
        this.items = objects;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request request = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_requests_layout, null);

        }
        // Lookup view for data population
        TextView tvID = (TextView) convertView.findViewById(R.id.requests_id_column);
        TextView tvRequestor = (TextView) convertView.findViewById(R.id.requestor_column);
        TextView tvDate = (TextView) convertView.findViewById(R.id.date_column);
        // Populate the data into the template view using the data object
        tvID.setText(String.format("%d", request.getID()));
        tvRequestor.setText(request.getSUBMITTED_BY());
        tvDate.setText(request.getREQUEST_DATE().toString());

        // Return the completed view to render on screen
        return convertView;
    }


}
