package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.san.logicuniversity_ad.R;


public class StoreDisbursementDetailsFragment extends Fragment {

    private static final String DISBURSEMENT_ID = "disbursementId";

    private String disbursementId;

    public StoreDisbursementDetailsFragment() {
        // Required empty public constructor
    }


    public static StoreDisbursementDetailsFragment newInstance(String disbursementId) {
        StoreDisbursementDetailsFragment fragment = new StoreDisbursementDetailsFragment();
        Bundle args = new Bundle();
        args.putString(DISBURSEMENT_ID, disbursementId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            disbursementId = getArguments().getString(DISBURSEMENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_disbursement_details, container, false);
    }


}
