package com.san.logicuniversity_ad.utils.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.DisbursementItem;

import java.util.ArrayList;

public class DisbursementDetailsAdapter extends ArrayAdapter<DisbursementItem> {

    private static ArrayList<DisbursementItem> items;
    private LayoutInflater inflater;

    public DisbursementDetailsAdapter(Context context, ArrayList<DisbursementItem> objects) {
        super(context, 0, objects);
        this.items = objects;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DisbursementItem item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_disbursement_details_layout, null);

        }
        // Lookup view for data population
        TextView tvDescription = (TextView) convertView.findViewById(R.id.disb_description_column);
        TextView tvUnitOfMeasure = (TextView) convertView.findViewById(R.id.disb_uom_column);
        TextView tvQuantityIssued = (TextView) convertView.findViewById(R.id.disb_quantity_column);

        // Populate the data into the template view using the data object
        tvDescription.setText(item.getDescription());
        tvUnitOfMeasure.setText(item.getUnitOfMeasure());
        tvQuantityIssued.setText(String.format("%d", item.getQtyIssued()));

        // Return the completed view to render on screen
        return convertView;
    }


}
