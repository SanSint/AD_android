package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.san.logicuniversity_ad.R;


public class StoreStockListFragment extends Fragment {

    public StoreStockListFragment() {
        // Required empty public constructor
    }


    public static StoreStockListFragment newInstance(String param1, String param2) {
        StoreStockListFragment fragment = new StoreStockListFragment();

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
        return inflater.inflate(R.layout.fragment_store_stock_list, container, false);
    }
}
