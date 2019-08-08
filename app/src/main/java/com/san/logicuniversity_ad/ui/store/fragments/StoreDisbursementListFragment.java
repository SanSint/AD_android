package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.san.logicuniversity_ad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreDisbursementListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreDisbursementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreDisbursementListFragment extends Fragment {

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

}
