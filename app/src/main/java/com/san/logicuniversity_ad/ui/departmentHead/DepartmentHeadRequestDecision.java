package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.utils.networkUtils.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.utils.networkUtils.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.utils.adaptors.PendingRequestDetailsAdapter;
import com.san.logicuniversity_ad.modals.RequestDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentHeadRequestDecision extends AppCompatActivity implements AsyncToServer.IServerResponse, View.OnClickListener {

    private ListView listView;
    private TextView tvRequestID;
    private TextView tvRequestor;
    private TextView tvTotalCost;
    private int selectedRequestID;
    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int actingHeadID;
    private Button approveButton;
    private Button rejectButton;
    private String decision;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_approve_requests);

        tvRequestID = findViewById(R.id.request_id_pending);
        tvRequestor = findViewById(R.id.requestor);
        listView = findViewById(R.id.dept_head_request_details);
        tvTotalCost = findViewById(R.id.pending_requests_total_cost);
        approveButton = findViewById(R.id.approve_pending_button);
        approveButton.setOnClickListener(this);
        approveButton.setBackgroundColor(Color.parseColor("#c8e6c9"));
        rejectButton = findViewById(R.id.reject_pending_button);
        rejectButton.setOnClickListener(this);
        rejectButton.setBackgroundColor(Color.parseColor("#ff7961"));

        Bundle bundle = getIntent().getExtras();
        selectedRequestID = bundle.getInt("selectedRequestID");
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");
        actingHeadID = bundle.getInt("actingHeadID");

        tvRequestID.setText(String.format("%d", selectedRequestID));

        decision = "";
    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all requests
        String endpt
                = BuildConfig.API_BASE_URL + "/api/getrequestdetails/" + String.format("%d",selectedRequestID);
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
                JSONArray requestDetailsArray = (JSONArray) jsonObj.get("result");
                ArrayList<RequestDetails> requestDetailsList = new ArrayList<RequestDetails>();
                double totalcost = 0;
                int len = requestDetailsArray.length();
                for (int i = 0; i < len; i++)
                {
                    JSONObject requestDetails = requestDetailsArray.getJSONObject(i);
                    String description = requestDetails.getString("DESCRIPTION");
                    int quantity = requestDetails.getInt("REQUEST_QUANTITY");
                    Double price = requestDetails.getDouble("FIRST_SUPP_PRICE");

                    totalcost += quantity*price;

                    RequestDetails detailsObj  = new RequestDetails();
                    detailsObj.setDESCRIPTION(description);
                    detailsObj.setQUANTITY(quantity);
                    detailsObj.setPRICE(price);

                    requestDetailsList.add(detailsObj);
                }

                tvRequestor.setText(requestDetailsArray.getJSONObject(0).getString("NAME"));
                tvTotalCost.setText("$ " + String.format("%.2f", totalcost));
                PendingRequestDetailsAdapter adapter
                        = new PendingRequestDetailsAdapter(this,requestDetailsList);

                listView.setAdapter(adapter);
            }
            else if (context.compareTo("approve") == 0)
            {
                String status = (String)jsonObj.get("status");
                System.out.println("status:" + status);

                Bundle bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putInt("actingHeadID", actingHeadID);
                bundle.putString("confirmedStatus", decision);
                bundle.putInt("confirmedRequestID", selectedRequestID);

                Intent intent = new Intent(getApplicationContext(), DepartmentHeadRequests.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }




        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerFailed() {

    }

    @Override
    public void onClick(View view) {
        JSONObject jsonObj = new JSONObject();
        String endpt = BuildConfig.API_BASE_URL + "/api/requestUpdate";
        Command command;
        switch (view.getId()){
            case R.id.approve_pending_button:
                decision = "APPROVED";
                try {
                    jsonObj.put("ID", selectedRequestID);
                    jsonObj.put("STATUS", decision);
                    jsonObj.put("APPROVED_BY", currentUserID);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                command = new Command(this,"approve",endpt,jsonObj);
                new AsyncToServer().execute(command);

                break;
            case R.id.reject_pending_button:
                decision = "REJECTED";
                try {
                    jsonObj.put("ID", selectedRequestID);
                    jsonObj.put("STATUS", decision);
                    jsonObj.put("APPROVED_BY", currentUserID);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                command = new Command(this,"approve",endpt,jsonObj);
                new AsyncToServer().execute(command);
                break;
            default:
                break;

        }

    }
}
