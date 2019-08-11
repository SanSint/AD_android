package com.san.logicuniversity_ad.utils.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.RetrivalItem;

import java.util.List;

public class RetrivalItemAdaptor extends RecyclerView.Adapter<RetrivalItemAdaptor.RetrivalItemViewHolder> {

    List<RetrivalItem> retrivalItemList;

    public RetrivalItemAdaptor(List<RetrivalItem> retrivalItemList) {
        this.retrivalItemList = retrivalItemList;
    }

    @Override
    public int getItemCount() {
        return this.retrivalItemList.size();
    }

    @Override
    public void onBindViewHolder(RetrivalItemViewHolder retrivalItemViewHolder, int i) {

        RetrivalItem ri = retrivalItemList.get(i);
        retrivalItemViewHolder.tvItemNumber.setText(ri.getItemNumber());
        retrivalItemViewHolder.tvCategory.setText(ri.getCategory());
        retrivalItemViewHolder.tvDescription.setText(ri.getDescription());
        retrivalItemViewHolder.tvQtyNeeded.setText(ri.getQtyNeeded() + "");
        retrivalItemViewHolder.etQtyRetrieved.setText(ri.getQtyNeeded() + "");

    }

    @Override
    public RetrivalItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.retrival_form_item_card, viewGroup, false);

        return new RetrivalItemViewHolder(itemView);
    }

    public class RetrivalItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemNumber;
        public TextView tvCategory;
        public TextView tvQtyNeeded;
        public TextView tvDescription;
        public EditText etQtyRetrieved;

        public RetrivalItemViewHolder(View v) {
            super(v);

            tvItemNumber = v.findViewById(R.id.tv_item_number);
            tvCategory = v.findViewById(R.id.tv_category);
            tvQtyNeeded = v.findViewById(R.id.tv_qty_needed);
            etQtyRetrieved = v.findViewById(R.id.et_qty_retrieved);
            tvDescription = v.findViewById(R.id.tv_desc);
        }
    }
}
