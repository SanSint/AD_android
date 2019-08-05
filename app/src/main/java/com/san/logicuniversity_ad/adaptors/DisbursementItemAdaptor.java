package com.san.logicuniversity_ad.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Disbursement;
import com.san.logicuniversity_ad.modals.DisbursementItem;

import java.util.List;

public class DisbursementItemAdaptor extends  RecyclerView.Adapter<DisbursementItemAdaptor.DisbursementItemViewHolder> {

    List<DisbursementItem> disbursementItemList;

    public DisbursementItemAdaptor(List<DisbursementItem> disbursementItemList) {
        this.disbursementItemList= disbursementItemList;
    }

    @NonNull
    @Override
    public DisbursementItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disbursement_item_card, parent, false);

        return new DisbursementItemAdaptor.DisbursementItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementItemViewHolder holder, int position) {
        DisbursementItem di = disbursementItemList.get(position);

        holder.tvItemNumber.setText(di.getItemNumber());
        holder.tvCategory.setText(di.getCategory());
        holder.tvDescription.setText(di.getDescription());
        holder.tvUnitOfMeasure.setText(di.getUnitOfMeasure());
        holder.tvQtyIssued.setText(di.getQtyIssued() + "");
        holder.etActualQtyIssued.setText(di.getActualQtyIssued() + "");
    }

    @Override
    public int getItemCount() {
        return disbursementItemList.size();
    }

    public class DisbursementItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvItemNumber;
        protected TextView tvCategory;
        protected TextView tvDescription;
        protected TextView tvUnitOfMeasure;
        protected TextView tvQtyIssued;
        protected EditText etActualQtyIssued;

        public DisbursementItemViewHolder(View v) {
            super(v);

            tvItemNumber = v.findViewById(R.id.tv_item_number);
            tvCategory = v.findViewById(R.id.tv_category);
            tvDescription= v.findViewById(R.id.tv_desc);
            tvUnitOfMeasure = v.findViewById(R.id.tv_uom);
            tvQtyIssued = v.findViewById(R.id.tv_qty_issued);
            etActualQtyIssued = v.findViewById(R.id.et_actual_qty_issued);
        }

    }
}
