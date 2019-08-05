package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.RetrivalItemAdaptor;
import com.san.logicuniversity_ad.modals.RetrivalItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Store_RetrivalForm extends AppCompatActivity
        implements View.OnClickListener, AsyncToServer.IServerResponse {

    private final String GET_ZONES_URL = BuildConfig.API_BASE_URL + "/store/zones";
    private final String GET_RETRIVAL_LIST_URL = BuildConfig.API_BASE_URL + "/store/retrival-list";
    private final String GET_RETRIVAL_LIST_BY_ZONE_URL = BuildConfig.API_BASE_URL + "/store/retrival-list/zone";

    Spinner zoneFilter;
    RecyclerView rvRetrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_retrival_form);

        zoneFilter = findViewById(R.id.zone_fliter);
        zoneFilter.setSelection(0);

        zoneFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                requestRetrivalItemList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btn = findViewById(R.id.btn_submit);
        btn.setOnClickListener(this);

        requestZones();

        rvRetrival = findViewById(R.id.retrieval_rv);
        rvRetrival.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvRetrival.setLayoutManager(llm);

    }

    public void onClick(View v) {
        Intent view = new Intent(this, Store_Success.class);
        startActivity(view);
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

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, zoneList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
