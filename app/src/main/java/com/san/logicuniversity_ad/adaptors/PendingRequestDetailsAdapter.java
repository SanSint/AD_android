package com.san.logicuniversity_ad.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.RequestDetails;

import java.util.ArrayList;

public class PendingRequestDetailsAdapter extends ArrayAdapter<RequestDetails> {

    private static ArrayList<RequestDetails> items;
    private LayoutInflater inflater;

    public PendingRequestDetailsAdapter(Context context, ArrayList<RequestDetails> objects) {
        super(context, 0, objects);
        this.items = objects;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RequestDetails requestdet = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_request_details_layout, null);

        }
        // Lookup view for data population
        TextView tvDescription = (TextView) convertView.findViewById(R.id.desciption_column);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.request_quantity_column);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.request_price_column);

        // Populate the data into the template view using the data object
        tvDescription.setText(requestdet.getDESCRIPTION());
        tvQuantity.setText(String.format("%d", requestdet.getQUANTITY()));
        tvPrice.setText(String.format("%.2f", requestdet.getPRICE()));

        // Return the completed view to render on screen
        return convertView;
    }


}
