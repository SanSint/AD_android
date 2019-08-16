package com.san.logicuniversity_ad.ui.departmentRep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.DisbursementDetailsAdapter;
import com.san.logicuniversity_ad.modals.DisbursementItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentRepConfirmDisbursement extends AppCompatActivity implements AsyncToServer.IServerResponse, View.OnClickListener {

    private ListView listView;
    private TextView tvDisbStatus;
    private TextView tvSwipeInstruction;
    private Button acknowledgeBtn;
    private View listHeader;
    private SwipeRefreshLayout swipeLayout;
    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int currentRequestID;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_rep_confirm_disbursement);


        listView = findViewById(R.id.dept_rep_disb_details);
        tvDisbStatus = findViewById(R.id.dept_rep_disb_id);
        tvSwipeInstruction = findViewById(R.id.swipe_instructions);
        acknowledgeBtn = findViewById(R.id.acknowledge_button);
        listHeader = findViewById(R.id.list_disb_header);
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //async task to get disbursement details
                checkForDisbursementsToAcknowledge();

            }
        });
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright,null),
                getResources().getColor(android.R.color.holo_green_light,null),
                getResources().getColor(android.R.color.holo_orange_light,null),
                getResources().getColor(android.R.color.holo_red_light,null)
        );

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");

        currentRequestID = 0;

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkForDisbursementsToAcknowledge();
    }

    //async task to get disbursement details
    public void checkForDisbursementsToAcknowledge() {
        String endpt
                = BuildConfig.API_BASE_URL + "/api/getdisbursementdetails/" + String.format("%d",currentDeptID);
        Command cmd = new Command(this, "get", endpt, null);
        new AsyncToServer().execute(cmd);
    }


    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {

            String context = (String) jsonObj.get("context");

            if (context.compareTo("get") == 0)
            {
                boolean isAvailable = jsonObj.getBoolean("isAvailable");
                if (isAvailable) {
                    JSONArray disbDetailsArray = (JSONArray) jsonObj.get("result");
                    ArrayList<DisbursementItem> disbDetailsList = new ArrayList<DisbursementItem>();

                    int len = disbDetailsArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject disbDetail = disbDetailsArray.getJSONObject(i);
                        String description = disbDetail.getString("DESCRIPTION");
                        int quantity = disbDetail.getInt("QUANTITY_ISSUED");
                        String unitOfMeasure = disbDetail.getString("UNIT_OF_MEASURE");
                        if (i == 0){
                            currentRequestID = disbDetail.getInt("REQUEST_ID");
                        }

                        DisbursementItem item = new DisbursementItem();
                        item.setDescription(description);
                        item.setActualQtyIssued(quantity);
                        item.setUnitOfMeasure(unitOfMeasure);

                        disbDetailsList.add(item);

                    }

                    tvSwipeInstruction.setVisibility(View.INVISIBLE);
                    tvDisbStatus.setText(R.string.acknowledge_disbursement_message);
                    tvDisbStatus.setVisibility(View.VISIBLE);

                    acknowledgeBtn.setOnClickListener(this);
                    acknowledgeBtn.setVisibility(View.VISIBLE);
                    listHeader.setVisibility(View.VISIBLE);

                    DisbursementDetailsAdapter adapter
                            = new DisbursementDetailsAdapter(this, disbDetailsList);

                    listView.setAdapter(adapter);
                }
                else{
                    tvSwipeInstruction.setVisibility(View.VISIBLE);
                    tvDisbStatus.setText(R.string.default_ack_status);
                    tvDisbStatus.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Check back later",Toast.LENGTH_SHORT).show();

                }

                swipeLayout.setRefreshing(false);
            }
            else if (context.compareTo("acknowledge") == 0)
            {
                String status = (String)jsonObj.get("status");
                System.out.println("status:" + status);

                Bundle bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putString("acknowledged", "true");

                Intent intent = new Intent(getApplicationContext(), DepartmentRepMain.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }




        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        JSONObject jsonObj = new JSONObject();
        String endpt = BuildConfig.API_BASE_URL + "/api/acknowledgeDisbursement";
        Command command;
        switch (view.getId()){
            case R.id.acknowledge_button:
                try {
                    jsonObj.put("ID", currentRequestID);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                command = new Command(this,"acknowledge",endpt,jsonObj);
                new AsyncToServer().execute(command);

                break;

            default:
                break;

        }

    }

    @Override
    public void onServerFailed() {

    }
}
