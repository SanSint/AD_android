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

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Disbursement;
import com.san.logicuniversity_ad.utils.adaptors.DisbursementAdaptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreDisbursementListFragment extends Fragment implements AsyncToServer.IServerResponse, DisbursementAdaptor.ViewClickListener {

    private OnFragmentInteractionListener mListener;

    private int clerkId = 1;
    private final String GET_DISBURSEMENT_LIST_URL = BuildConfig.API_BASE_URL + "/api/PendingDisbursements";

    RecyclerView rvDisbursement;

    public StoreDisbursementListFragment() {
        // Required empty public constructor
    }


    public static StoreDisbursementListFragment newInstance(String param1, String param2) {
        StoreDisbursementListFragment fragment = new StoreDisbursementListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_disbursement_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvDisbursement = view.findViewById(R.id.rv_disbursements);
        rvDisbursement.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvDisbursement.setLayoutManager(layoutManager);

        requestDisbursementList();
    }

    public void requestDisbursementList() {
        rvDisbursement.setAdapter(null);
        Command cmd = new Command(this, "getDisbursements", GET_DISBURSEMENT_LIST_URL, null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onClickViewDetails(int disbursementId, boolean isEditable) {
        if (mListener != null) {
            mListener.onViewDisbursementDetails(disbursementId, isEditable);
        }
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getDisbursements") == 0) {
                onGetDisbursementList(jsonObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGetDisbursementList(JSONObject jsonObj) {
        try {
            ArrayList<Disbursement> disbursementArrayList= new ArrayList<>();
            JSONArray riArr = (JSONArray) jsonObj.get("result");

            for (int i = 0, count = riArr.length(); i < count; i++) {
                JSONObject riJson = riArr.getJSONObject(i);
                Disbursement d = new Disbursement(
                        riJson.getInt("disbursementId"),
                        riJson.getString("collectionPoint"),
                        riJson.getString("deliveredBy"),
                        riJson.getString("status"));

                disbursementArrayList.add(d);
            }

            DisbursementAdaptor da = new DisbursementAdaptor(disbursementArrayList, this);
            rvDisbursement.setAdapter(da);


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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onViewDisbursementDetails(int disbursementId, boolean isEditable);
    }

}
