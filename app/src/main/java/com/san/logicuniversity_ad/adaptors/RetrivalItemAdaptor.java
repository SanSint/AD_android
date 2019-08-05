package com.san.logicuniversity_ad.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.RetrivalItem;
import com.san.logicuniversity_ad.viewHolders.RetrivalItemViewHolder;

import java.util.List;

public class RetrivalItemAdaptor extends RecyclerView.Adapter<RetrivalItemViewHolder> {

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
        retrivalItemViewHolder.tvZone.setText(ri.getZone());
        retrivalItemViewHolder.tvQtyNeeded.setText(ri.getQtyNeeded() + "");
        retrivalItemViewHolder.etQtyRetrieved.setText(ri.getQtyNeeded() + "");

    }

    @Override
    public RetrivalItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.retrival_form_item_card, viewGroup, false);

        return new RetrivalItemViewHolder(itemView);
    }
}
