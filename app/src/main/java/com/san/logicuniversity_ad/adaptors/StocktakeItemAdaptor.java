package com.san.logicuniversity_ad.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.DisbursementItem;
import com.san.logicuniversity_ad.modals.Stocktake;
import com.san.logicuniversity_ad.modals.StocktakeItem;

import java.util.List;

public class StocktakeItemAdaptor extends  RecyclerView.Adapter<StocktakeItemAdaptor.StocktakeItemViewHolder> {

    List<StocktakeItem> stocktakeItemList;

    public StocktakeItemAdaptor(List<StocktakeItem> stocktakeItemList) {
        this.stocktakeItemList = stocktakeItemList;
    }

    @NonNull
    @Override
    public StocktakeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocktake_item_card, parent, false);

        return new StocktakeItemAdaptor.StocktakeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StocktakeItemViewHolder holder, int position) {
        StocktakeItem si = stocktakeItemList.get(position);

        holder.tvItemNumber.setText(si.getItemNumber());
        holder.tvCategory.setText(si.getCategory());
        holder.tvDescription.setText(si.getDescription());
        holder.tvUnitOfMeasure.setText(si.getUnitOfMeasure());
        holder.tvQty.setText(si.getQty() + "");
        holder.etActualQty.setText(si.getQtyActual() + "");
    }

    @Override
    public int getItemCount() {
        return stocktakeItemList.size();
    }

    public class StocktakeItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvItemNumber;
        protected TextView tvCategory;
        protected TextView tvDescription;
        protected TextView tvUnitOfMeasure;
        protected TextView tvQty;
        protected EditText etActualQty;

        public StocktakeItemViewHolder(View v) {
            super(v);

            tvItemNumber = v.findViewById(R.id.tv_item_number);
            tvCategory = v.findViewById(R.id.tv_category);
            tvDescription= v.findViewById(R.id.tv_desc);
            tvUnitOfMeasure = v.findViewById(R.id.tv_uom);
            tvQty= v.findViewById(R.id.tv_qty);
            etActualQty= v.findViewById(R.id.et_actual_qty);
        }

    }
}
