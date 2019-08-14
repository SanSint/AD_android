package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.RetrivalItem;
import com.san.logicuniversity_ad.utils.adaptors.RetrivalItemAdaptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class StoreRetrivalListFragment extends Fragment implements AsyncToServer.IServerResponse {

    private final String GET_RETRIVAL_LIST_URL = BuildConfig.API_BASE_URL + "/store/retrivalList";

    RecyclerView rvRetrival;


    public StoreRetrivalListFragment() {
    }

    public static StoreRetrivalListFragment newInstance(String param1, String param2) {
        StoreRetrivalListFragment fragment = new StoreRetrivalListFragment();
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
        return inflater.inflate(R.layout.fragment_store_retrival_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvRetrival = view.findViewById(R.id.retrieval_rv);
        rvRetrival.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvRetrival.setLayoutManager(layoutManager);

        requestRetrivalItemList();
    }

    private void requestRetrivalItemList() {
        Command cmd = new Command(this, "getRetrivalList", GET_RETRIVAL_LIST_URL, null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getRetrivalList") == 0) {
                onGetRetrivalList(jsonObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void onGetRetrivalList(JSONObject jsonObj) {
        try {
            ArrayList<RetrivalItem> retrivalItemArrayList = new ArrayList<>();
            JSONArray riArr = (JSONArray) jsonObj.get("result");

            for (int i = 0, count = riArr.length(); i < count; i++) {
                JSONObject riJson = riArr.getJSONObject(i);
                RetrivalItem ri = new RetrivalItem(
                        riJson.getString("productId"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getInt("quantityNeeded"),
                        riJson.getInt("quantityNeeded"));

                retrivalItemArrayList.add(ri);
            }

            RetrivalItemAdaptor ra = new RetrivalItemAdaptor(retrivalItemArrayList);
            rvRetrival.setAdapter(ra);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
