package com.san.logicuniversity_ad.utils.adaptors;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.RetrivalItem;
import com.san.logicuniversity_ad.utils.InputMinMaxFilter;

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
    public void onBindViewHolder(RetrivalItemViewHolder retrivalItemViewHolder, final int position) {

        RetrivalItem ri = retrivalItemList.get(position);
        retrivalItemViewHolder.tvItemNumber.setText(ri.getItemNumber());
        retrivalItemViewHolder.tvCategory.setText(ri.getCategory());
        retrivalItemViewHolder.tvDescription.setText(ri.getDescription());
        retrivalItemViewHolder.tvQtyNeeded.setText(ri.getQtyNeeded() + "");
        retrivalItemViewHolder.etQtyRetrieved.setText(ri.getQtyNeeded() + "");
        retrivalItemViewHolder.etQtyRetrieved.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    retrivalItemList.get(position).setQtyRetrieved(Integer.parseInt(charSequence.toString()));
                } else {
                    retrivalItemList.get(position).setQtyRetrieved(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        retrivalItemViewHolder.etQtyRetrieved.setFilters(new InputFilter[]{new InputMinMaxFilter(0, ri.getQtyNeeded())});
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

    public List<RetrivalItem> getRetrivalItemList() {
        return retrivalItemList;
    }
}
