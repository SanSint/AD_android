package com.san.logicuniversity_ad.utils.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Disbursement;
import com.san.logicuniversity_ad.ui.store.Store_ViewDisbursementList;

import java.util.List;

public class DisbursementAdaptor extends RecyclerView.Adapter<DisbursementAdaptor.DisbursementViewHolder> {

    List<Disbursement> disbursementList;
    private Context context;

    public DisbursementAdaptor(List<Disbursement> disbursementList, Context context) {
        this.disbursementList = disbursementList;
        this.context = context;
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
        holder.tvDisbursementId.setText(d.getDisbursementId());
        holder.tvDoneBy.setText(d.getDoneBy());
        holder.tvDepartment.setText(d.getDepartment());
    }

    @Override
    public int getItemCount() {
        return disbursementList.size();
    }

    public class DisbursementViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvDisbursementId;
        protected TextView tvDoneBy;
        protected TextView tvDepartment;
        protected Button btnView;
        protected CardView cvDisbursementCard;

        public DisbursementViewHolder(View v) {
            super(v);

            tvDisbursementId = v.findViewById(R.id.tv_disbursement_id);
            tvDoneBy = v.findViewById(R.id.tv_done_by);
            tvDepartment = v.findViewById(R.id.tv_department);
            btnView = v.findViewById(R.id.btn_view);
            cvDisbursementCard = v.findViewById(R.id.disbursement_card);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), Store_ViewDisbursementList.class);
                    i.putExtra("disbursementId", tvDisbursementId.getText());
                    context.startActivity(i);

//                    view.setVisibility(View.GONE);
//                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_frame, new StoreDisbursementDetailsFragment())
//                            .commit();

                }
            });
        }

    }
}
