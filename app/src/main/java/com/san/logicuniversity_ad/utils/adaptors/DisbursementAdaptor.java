package com.san.logicuniversity_ad.utils.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Disbursement;

import java.util.List;

public class DisbursementAdaptor extends RecyclerView.Adapter<DisbursementAdaptor.DisbursementViewHolder> {

    List<Disbursement> disbursementList;
    ViewClickListener mListener;


    public DisbursementAdaptor(List<Disbursement> disbursementList, ViewClickListener mListener) {
        this.disbursementList = disbursementList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DisbursementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disbursement_card, parent, false);

        return new DisbursementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementViewHolder holder, int position) {
        Disbursement d = disbursementList.get(position);
        holder.tvDisbursementId.setText(d.getDisbursementId() + "");
        holder.tvDoneBy.setText(d.getDoneBy().toLowerCase() != "null" ? d.getDoneBy() : "");
        holder.tvDepartment.setText(d.getDepartment());
        holder.tvStatus.setText(d.getStatus());
        Log.i("DEBUG", d.getDoneBy());
    }

    @Override
    public int getItemCount() {
        return disbursementList.size();
    }


    public interface ViewClickListener {
        void onClickViewDetails(int disbursementId, boolean isEditable);
    }


    public class DisbursementViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvDisbursementId;
        protected TextView tvDoneBy;
        protected TextView tvDepartment;
        protected TextView tvStatus;
        protected CardView cvDisbursementCard;

        public DisbursementViewHolder(View v) {
            super(v);

            tvDisbursementId = v.findViewById(R.id.tv_disbursement_id);
            tvDoneBy = v.findViewById(R.id.tv_done_by);
            tvDepartment = v.findViewById(R.id.tv_department);
            tvStatus = v.findViewById(R.id.tv_status);
            cvDisbursementCard = v.findViewById(R.id.disbursement_card);

            cvDisbursementCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String status = tvStatus.getText().toString();
                    boolean isEditable = status.equals("PENDING DELIVERY");

                    mListener.onClickViewDetails(Integer.parseInt(tvDisbursementId.getText().toString()), isEditable);

                }
            });
        }


    }
}
