package com.san.logicuniversity_ad.viewHolders;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;

public class RetrivalItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemNumber;
    public TextView tvCategory;
    public TextView tvZone;
    public TextView tvQtyNeeded;
    public EditText etQtyRetrieved;

    public RetrivalItemViewHolder(View v) {
        super(v);

        tvItemNumber = v.findViewById(R.id.tv_item_number);
        tvCategory = v.findViewById(R.id.tv_category);
        tvZone = v.findViewById(R.id.tv_zone);
        tvQtyNeeded = v.findViewById(R.id.tv_qty_needed);
        etQtyRetrieved = v.findViewById(R.id.et_qty_retrieved);
    }
}
