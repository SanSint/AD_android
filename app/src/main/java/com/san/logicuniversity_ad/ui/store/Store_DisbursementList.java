package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.DisbursementAdaptor;
import com.san.logicuniversity_ad.modals.Disbursement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Store_DisbursementList extends AppCompatActivity
    implements View.OnClickListener, AsyncToServer.IServerResponse {

    private final String GET_DISBURSEMENT_LIST_URL = BuildConfig.API_BASE_URL + "/store/disbursement-list";

    RecyclerView rvDisbursement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_disbursementlist);

        rvDisbursement = findViewById(R.id.rv_disbursements);
        rvDisbursement.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvDisbursement.setLayoutManager(llm);

        requestDisbursementList();
    }
    public void onClick(View v)
    {
        Intent view=new Intent(this, Store_Success.class);
        startActivity(view);
    }

    private void requestDisbursementList() {
        Command cmd = new Command(this, "getDisbursements", GET_DISBURSEMENT_LIST_URL, null);
        new AsyncToServer().execute(cmd);
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
                        riJson.getString("disbursementId"),
                        riJson.getString("department"),
                        riJson.getString("doneBy"));

                disbursementArrayList.add(d);
            }

            DisbursementAdaptor da = new DisbursementAdaptor(disbursementArrayList);
            rvDisbursement.setAdapter(da);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
