package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.san.logicuniversity_ad.utils.networkUtils.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.utils.networkUtils.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.DisbursementItem;
import com.san.logicuniversity_ad.utils.adaptors.DisbursementItemAdaptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class StoreDisbursementDetailsFragment extends Fragment implements AsyncToServer.IServerResponse {

    private OnFragmentInteractionListener mListener;
    private int disbursementId;
    private boolean isEditable;
    private DisbursementItemAdaptor disbursementItemAdaptor;

    private static final String DISBURSEMENT_ID = "disbursementId";
    private static final String IS_EDITABLE = "isEditable";
    private final String GET_DISBURSEMENT_URL = BuildConfig.API_BASE_URL + "/api/DisbursementDetails";
    private final String POST_DISBURSEMENT_URL = BuildConfig.API_BASE_URL + "/api/DisbursementUpdate";

    RecyclerView rvDisbursementItem;
    TextView tvDepartment;
    TextView tvCollectionPoint;
    TextView tvDeptRep;
    ImageButton btnBack;
    FloatingActionButton btnSubmit;

    public StoreDisbursementDetailsFragment() {
        // Required empty public constructor
    }


    public static StoreDisbursementDetailsFragment newInstance(int disbursementId, boolean isEditable) {
        StoreDisbursementDetailsFragment fragment = new StoreDisbursementDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(DISBURSEMENT_ID, disbursementId);
        args.putBoolean(IS_EDITABLE, isEditable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            disbursementId = getArguments().getInt(DISBURSEMENT_ID);
            isEditable = getArguments().getBoolean(IS_EDITABLE);
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

        tvDepartment = view.findViewById(R.id.tv_collection_point);
        tvCollectionPoint = view.findViewById(R.id.tv_collections);
        tvDeptRep = view.findViewById(R.id.tv_dep_rep);
        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBackToDisbursementList();
            }
        });

        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDisbursements();
            }
        });
        if(!this.isEditable) {
            btnSubmit.setEnabled(false);
        }

        requestDisbursement();
    }

    public void requestDisbursement() {
        Command cmd = new Command(this, "getDisbursement", GET_DISBURSEMENT_URL + "/" + disbursementId, null);
        new AsyncToServer().execute(cmd);
    }

    private void submitDisbursements() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = pref.getString("username", null);

        JSONObject obj = new JSONObject();
        try {
            obj.put("userName", username);

            JSONArray jsonDIs = new JSONArray();
            for (DisbursementItem di: disbursementItemAdaptor.getDisbursementItemList()) {
                JSONObject jsonDi = new JSONObject();
                jsonDi.put("DISBURSEMENT_ID", disbursementId);
                jsonDi.put("PRODUCT_ID", di.getItemNumber());
                jsonDi.put("QUANTITY_COLLECTED", di.getQtyCollected());
                jsonDi.put("QUANTITY_ISSUED", di.getQtyIssued());
                jsonDi.put("REASON", di.getReason());
                jsonDIs.put(jsonDi);
            }
            obj.put("disbursementDetails", jsonDIs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(this, "postDisbursement", POST_DISBURSEMENT_URL, obj);
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
            } else if(context.compareTo("postDisbursement") == 0) {
                onAfterPostDisbursement(jsonObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerFailed() {

    }

    private void onGetDisbursement(JSONObject jsonObj) {
        try {
            JSONObject disbursement = jsonObj.getJSONObject("result");
            tvDepartment.setText(disbursement.getString("department"));
            tvCollectionPoint.setText(disbursement.getString("collectionPoint"));
            tvDeptRep.setText(disbursement.getString("depRepName"));

            ArrayList<DisbursementItem> disItemAL = new ArrayList<>();
            JSONArray diArr = (JSONArray) disbursement.get("itemList");

            for (int i = 0, count = diArr.length(); i < count; i++) {
                JSONObject riJson = diArr.getJSONObject(i);
                DisbursementItem di = new DisbursementItem(
                        riJson.getString("productId"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getString("uOfMeasure"),
                        riJson.getInt("QtyCollected"),
                        riJson.getInt("QtyIssue"),
                        !riJson.getString("reason").toLowerCase().equals("null") ? riJson.getString("reason") : "");

                disItemAL.add(di);
            }

            disbursementItemAdaptor = new DisbursementItemAdaptor(disItemAL, this.isEditable);
            rvDisbursementItem.setAdapter(disbursementItemAdaptor);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAfterPostDisbursement(JSONObject jsonObject) {
        try {
            int numOfAd = jsonObject.getInt("numOfAd");
            if(numOfAd >= 0) {
                btnBack.performClick();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("The disbursement has been successfully delivered!")
                        .setTitle("Disbursement Successful!");

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        } catch ( Exception e) {
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
