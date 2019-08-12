package com.san.logicuniversity_ad.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Employee;

import java.util.ArrayList;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {

    private static ArrayList<Employee> items;
    private LayoutInflater inflater;

    public EmployeeListAdapter(Context context, ArrayList<Employee> objects) {
        super(context, 0, objects);
        this.items = objects;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee employee = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dropdown_item_employee, null);

        }
        // Lookup view for data population
        TextView tvEmployeeName = (TextView) convertView.findViewById(R.id.dropdown_employee_name);

        // Populate the data into the template view using the data object
        tvEmployeeName.setText(employee.getNAME());


        // Return the completed view to render on screen
        return convertView;
    }


}
