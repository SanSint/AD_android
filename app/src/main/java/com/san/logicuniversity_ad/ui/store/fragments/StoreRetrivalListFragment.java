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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    private final String GET_RETRIVAL_LIST_URL = BuildConfig.API_BASE_URL + "/api/retrivalList";
    private final String POST_RETRIVAL_LIST_URL = BuildConfig.API_BASE_URL + "/api/RetrivalListSubmit";

    RecyclerView rvRetrival;
    FloatingActionButton btnSubmit;

    RetrivalItemAdaptor retrivalItemAdaptor;


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

        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRetrivalList();
            }
        });

        requestRetrivalItemList();
    }

    public void requestRetrivalItemList() {
        Command cmd = new Command(this, "getRetrivalList", GET_RETRIVAL_LIST_URL, null);
        new AsyncToServer().execute(cmd);
    }

    private void submitRetrivalList() {
        JSONObject obj = new JSONObject();
        try {

            JSONArray jsonRIs = new JSONArray();
            for (RetrivalItem ri: retrivalItemAdaptor.getRetrivalItemList()) {
                JSONObject jsonDi = new JSONObject();
                jsonDi.put("productId", ri.getItemNumber());
                jsonDi.put("quantityNeeded", ri.getQtyNeeded());
                jsonDi.put("quantityRetrieved", ri.getQtyRetrieved());
                jsonRIs.put(jsonDi);
            }
            obj.put("retrievalForms", jsonRIs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(this, "postRetrivalList", POST_RETRIVAL_LIST_URL, obj);
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
            } else if (context.compareTo("postRetrivalList") == 0) {
                onAfterPostRetrivalList(jsonObj);
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

            retrivalItemAdaptor = new RetrivalItemAdaptor(retrivalItemArrayList);
            rvRetrival.setAdapter(retrivalItemAdaptor);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAfterPostRetrivalList(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("ok")) {
                Toast.makeText(getContext(), "Successfully submitted the data!", Toast.LENGTH_SHORT).show();
                requestRetrivalItemList();
            }

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }



}
