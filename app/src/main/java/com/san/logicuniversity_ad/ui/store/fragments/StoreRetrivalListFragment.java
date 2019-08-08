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

    private final String GET_ZONES_URL = BuildConfig.API_BASE_URL + "/store/zones";
    private final String GET_RETRIVAL_LIST_URL = BuildConfig.API_BASE_URL + "/store/retrival-list";
    private final String GET_RETRIVAL_LIST_BY_ZONE_URL = BuildConfig.API_BASE_URL + "/store/retrival-list/zone";

    Spinner zoneFilter;
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

        zoneFilter = view.findViewById(R.id.zone_filter);
//        zoneFilter.setSelection(0);

        zoneFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                requestRetrivalItemList();
                TextView t = ((TextView)adapterView.getChildAt(0));
                t.setTextColor(Color.WHITE);
                t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        requestZones();


        rvRetrival = view.findViewById(R.id.retrieval_rv);
        rvRetrival.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvRetrival.setLayoutManager(layoutManager);

    }

    private void requestZones() {
        Command cmd = new Command(this, "getZones", GET_ZONES_URL, null);
        new AsyncToServer().execute(cmd);
    }

    private void requestRetrivalItemList() {
        String selectedZone = zoneFilter.getSelectedItem().toString();

        if (selectedZone.equals("All")) {
            Command cmd = new Command(this, "getRetrivalList", GET_RETRIVAL_LIST_URL, null);
            new AsyncToServer().execute(cmd);
        } else {
            Command cmd = new Command(this, "getRetrivalList", GET_RETRIVAL_LIST_BY_ZONE_URL + "/" + selectedZone, null);
            new AsyncToServer().execute(cmd);
        }
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getZones") == 0) {
                onGetZones(jsonObj);
            } else if (context.compareTo("getRetrivalList") == 0) {
                onGetRetrivalList(jsonObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void onGetZones(JSONObject jsonObj) {
        try {
            ArrayList<String> zoneList = new ArrayList<>();
            zoneList.add("All");
            JSONArray zonesJArr = (JSONArray) jsonObj.get("result");

            for (int i = 0, count = zonesJArr.length(); i < count; i++) {
                zoneList.add(zonesJArr.getString(i));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, zoneList);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            zoneFilter.setAdapter(adapter);
            zoneFilter.setSelection(0);

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
                        riJson.getString("itemNumber"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getString("Zone"),
                        riJson.getInt("QtyNeeded"),
                        riJson.getInt("QtyNeeded"));

                retrivalItemArrayList.add(ri);
            }

            RetrivalItemAdaptor ra = new RetrivalItemAdaptor(retrivalItemArrayList);
            rvRetrival.setAdapter(ra);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
