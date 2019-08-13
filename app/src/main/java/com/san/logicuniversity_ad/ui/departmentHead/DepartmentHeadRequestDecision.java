package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.PendingRequestDetailsAdapter;
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
    private Button approveButton;
    private Button rejectButton;

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
        rejectButton = findViewById(R.id.reject_pending_button);
        rejectButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        selectedRequestID = bundle.getInt("selectedRequestID");
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");

        tvRequestID.setText(String.format("%d", selectedRequestID));


    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all requests
        String endpt
                = "http://10.0.2.2:44361/api/getrequestdetails/" + String.format("%d",selectedRequestID);
        //endpt for using actual phone to debug
//        String endpt
//                = "http://192.168.1.116:45455/api/getrequestdetails/" + String.format("%d",selectedRequestID);
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
    public void onClick(View view) {
        JSONObject jsonObj = new JSONObject();
        String endpt = "http://10.0.2.2:44361/api/requestUpdate";
        Command command;
        switch (view.getId()){
            case R.id.approve_pending_button:
                try {
                    jsonObj.put("ID", selectedRequestID);
                    jsonObj.put("STATUS", "APPROVED");
                    jsonObj.put("APPROVED_BY", currentUserID);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                command = new Command(this,"approve",endpt,jsonObj);
                new AsyncToServer().execute(command);

                break;
            case R.id.reject_pending_button:
                try {
                    jsonObj.put("ID", selectedRequestID);
                    jsonObj.put("STATUS", "REJECTED");
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
