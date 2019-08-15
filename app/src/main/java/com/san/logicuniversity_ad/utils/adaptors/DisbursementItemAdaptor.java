package com.san.logicuniversity_ad.utils.adaptors;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.DisbursementItem;

import java.util.List;

public class DisbursementItemAdaptor extends  RecyclerView.Adapter<DisbursementItemAdaptor.DisbursementItemViewHolder> {

    List<DisbursementItem> disbursementItemList;
    boolean isEditable;

    public DisbursementItemAdaptor(List<DisbursementItem> disbursementItemList, boolean isEditable) {
        this.disbursementItemList= disbursementItemList;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public DisbursementItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disbursement_item_card, parent, false);

        return new DisbursementItemAdaptor.DisbursementItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementItemViewHolder holder, final int position) {
        DisbursementItem di = disbursementItemList.get(position);

        holder.tvItemNumber.setText(di.getItemNumber());
        holder.tvCategory.setText(di.getCategory());
        holder.tvDescription.setText(di.getDescription());
        holder.tvUnitOfMeasure.setText(di.getUnitOfMeasure());
        holder.tvQtyIssued.setText(di.getQtyCollected() + "");
        holder.etActualQtyIssued.setText((isEditable ? di.getQtyCollected() : di.getQtyIssued()) + "");
        holder.etActualQtyIssued.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    disbursementItemList.get(position).setQtyIssued(Integer.parseInt(charSequence.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.etReason.setText(di.getReason());
        holder.etReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                disbursementItemList.get(position).setReason(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if(!isEditable) {
            holder.etActualQtyIssued.setEnabled(false);
            holder.etReason.setEnabled(false);
        }
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
        protected EditText etReason;

        public DisbursementItemViewHolder(View v) {
            super(v);

            tvItemNumber = v.findViewById(R.id.tv_item_number);
            tvCategory = v.findViewById(R.id.tv_category);
            tvDescription= v.findViewById(R.id.tv_desc);
            tvUnitOfMeasure = v.findViewById(R.id.tv_uom);
            tvQtyIssued = v.findViewById(R.id.tv_qty_issued);
            etActualQtyIssued = v.findViewById(R.id.et_actual_qty_issued);
            etReason = v.findViewById(R.id.et_reason);
        }

    }

    public List<DisbursementItem> getDisbursementItemList() {
        return this.disbursementItemList;
    }
}
