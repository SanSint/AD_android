package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.DisbursementItem;
import com.san.logicuniversity_ad.utils.adaptors.DisbursementItemAdaptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class StoreDisbursementDetailsFragment extends Fragment implements AsyncToServer.IServerResponse {

    private OnFragmentInteractionListener mListener;
    private int disbursementId;

    private static final String DISBURSEMENT_ID = "disbursementId";
    private final String GET_DISBURSEMENT_URL = BuildConfig.API_BASE_URL + "/api/DisbursementDetails";

    RecyclerView rvDisbursementItem;
    TextView tvDepartment;
    TextView tvCollectionPoint;
    TextView tvDeptRep;
    ImageButton btnBack;

    public StoreDisbursementDetailsFragment() {
        // Required empty public constructor
    }


    public static StoreDisbursementDetailsFragment newInstance(int disbursementId) {
        StoreDisbursementDetailsFragment fragment = new StoreDisbursementDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(DISBURSEMENT_ID, disbursementId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            disbursementId = getArguments().getInt(DISBURSEMENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_disbursement_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDisbursementItem = view.findViewById(R.id.rv_disbursement_items);
        rvDisbursementItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvDisbursementItem.setLayoutManager(layoutManager);

        tvDepartment = view.findViewById(R.id.tv_department);
        tvCollectionPoint = view.findViewById(R.id.tv_collections);
        tvDeptRep = view.findViewById(R.id.tv_dep_rep);
        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBackToDisbursementList();
            }
        });

        requestDisbursement();
    }

    private void requestDisbursement() {
        Command cmd = new Command(this, "getDisbursement", GET_DISBURSEMENT_URL + "/" + disbursementId, null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getDisbursement") == 0) {
                onGetDisbursement(jsonObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGetDisbursement(JSONObject jsonObj) {
        try {
            JSONObject disbursement = jsonObj.getJSONObject("result");
            tvDepartment.setText(disbursement.getString("department"));
            tvCollectionPoint.setText(disbursement.getString("collectionPoint"));
            tvDeptRep.setText(disbursement.getString("deptRepName"));

            ArrayList<DisbursementItem> disItemAL = new ArrayList<>();
            JSONArray diArr = (JSONArray) disbursement.get("itemList");

            for (int i = 0, count = diArr.length(); i < count; i++) {
                JSONObject riJson = diArr.getJSONObject(i);
                DisbursementItem di = new DisbursementItem(
                        riJson.getString("productId"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getString("uOfMeasure"),
                        riJson.getInt("QtyIssue"),
                        riJson.getInt("QtyIssue"));

                disItemAL.add(di);
            }

            DisbursementItemAdaptor da = new DisbursementItemAdaptor(disItemAL);
            rvDisbursementItem.setAdapter(da);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onBackToDisbursementList();
    }
}
