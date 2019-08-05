package com.san.logicuniversity_ad.adaptors;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Disbursement;
import com.san.logicuniversity_ad.modals.Stocktake;
import com.san.logicuniversity_ad.ui.store.Store_StockChecking;
import com.san.logicuniversity_ad.ui.store.Store_ViewDisbursementList;

import java.util.List;

public class StocktakeAdaptor extends  RecyclerView.Adapter<StocktakeAdaptor.StocktakeViewHolder> {

    List<Stocktake> stocktakeList;

    public StocktakeAdaptor(List<Stocktake> stocktakeList) {
        this.stocktakeList= stocktakeList;
    }

    @NonNull
    @Override
    public StocktakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_check_card, parent, false);

        return new StocktakeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StocktakeViewHolder holder, int position) {
        Stocktake s = stocktakeList.get(position);
        holder.tvStocktakeId.setText(s.getStocktakeId());
        holder.tvDoneBy.setText(s.getDoneBy());
        holder.tvMonth.setText(s.getMonth());
    }

    @Override
    public int getItemCount() {
        return stocktakeList.size();
    }

    public class StocktakeViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvStocktakeId;
        protected TextView tvDoneBy;
        protected TextView tvMonth;
        protected Button btnView;

        public StocktakeViewHolder(View v) {
            super(v);

            tvStocktakeId = v.findViewById(R.id.tv_stocktake_id);
            tvDoneBy = v.findViewById(R.id.tv_done_by);
            tvMonth= v.findViewById(R.id.tv_month);
            btnView = v.findViewById(R.id.btn_view);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), Store_StockChecking.class);
                    i.putExtra("stocktakeId", tvStocktakeId.getText());
                    view.getContext().startActivity(i);
                }
            });
        }

    }
}
