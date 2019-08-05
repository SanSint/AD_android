package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.DisbursementAdaptor;
import com.san.logicuniversity_ad.adaptors.DisbursementItemAdaptor;
import com.san.logicuniversity_ad.modals.Disbursement;
import com.san.logicuniversity_ad.modals.DisbursementItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Store_ViewDisbursementList extends AppCompatActivity implements AsyncToServer.IServerResponse {

    private final String GET_DISBURSEMENT_URL = BuildConfig.API_BASE_URL + "/store/disbursement/Id";

    RecyclerView rvDisbursementItem;
    String selectedDisbursementId;

    TextView tvDepartment;
    TextView tvCollectionPoint;
    TextView tvDeptRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view_disbursement_list);

        rvDisbursementItem = findViewById(R.id.rv_disbursement_items);
        rvDisbursementItem.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvDisbursementItem.setLayoutManager(llm);

        Bundle extra = getIntent().getExtras();
        selectedDisbursementId = extra.getString("disbursementId");

        tvDepartment = findViewById(R.id.tv_department);
        tvCollectionPoint = findViewById(R.id.tv_collections);
        tvDeptRep = findViewById(R.id.tv_dep_rep);

        requestDisbursement();
    }

    private void requestDisbursement() {
        Command cmd = new Command(this, "getDisbursement", GET_DISBURSEMENT_URL + "/" + selectedDisbursementId, null);
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGetDisbursement(JSONObject jsonObj) {
        try {
            JSONObject disbursement = jsonObj.getJSONObject("result");
            tvDepartment.setText(disbursement.getString("department"));
            tvCollectionPoint.setText(disbursement.getString("collectionPoint"));
            tvDeptRep.setText(disbursement.getString("deptRep"));

            ArrayList<DisbursementItem> disItemAL = new ArrayList<>();
            JSONArray diArr = (JSONArray) disbursement.get("itemList");

            for (int i = 0, count = diArr.length(); i < count; i++) {
                JSONObject riJson = diArr.getJSONObject(i);
                DisbursementItem di = new DisbursementItem(
                        riJson.getString("itemNumber"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getString("unitOfMeasure"),
                        riJson.getInt("qtyIssue"),
                        riJson.getInt("qtyIssue"));

                disItemAL.add(di);
            }

            DisbursementItemAdaptor da = new DisbursementItemAdaptor(disItemAL);
            rvDisbursementItem.setAdapter(da);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
