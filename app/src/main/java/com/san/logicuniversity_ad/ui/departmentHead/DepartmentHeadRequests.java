package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.utils.networkUtils.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.utils.networkUtils.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.utils.adaptors.PendingRequestsAdapter;
import com.san.logicuniversity_ad.modals.Request;
import com.san.logicuniversity_ad.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class DepartmentHeadRequests extends AppCompatActivity implements AsyncToServer.IServerResponse {

    private ListView listView;
    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int actingHeadID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_pending_requests);

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");
        actingHeadID = bundle.getInt("actingHeadID");
        if (bundle.getString("confirmedStatus") != null){
            String confirmedStatus = bundle.getString("confirmedStatus");
            int confirmedRequestID = bundle.getInt("confirmedRequestID");
            Toast.makeText(this,
                    "Request ID: "+ confirmedRequestID + " has been " + confirmedStatus + ".", Toast.LENGTH_LONG)
                    .show();
        }
        listView = findViewById(R.id.dept_head_requests_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all requests
        String endpt = BuildConfig.API_BASE_URL + "/api/getrequests/" + String.format("%d",currentDeptID);
        Command cmd = new Command(this, "get", endpt, null);
        new AsyncToServer().execute(cmd);
    }


    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            JSONArray requestsArray = (JSONArray) jsonObj.get("result");
            ArrayList<Request> requestlist = new ArrayList<Request>();
            int len = requestsArray.length();
            for (int i = 0; i < len; i++)
            {
                JSONObject request = requestsArray.getJSONObject(i);
                int requestID = request.getInt("ID");
                String requestor = request.getString("NAME");
                String date = request.getString("REQUEST_DATE");
                LocalDate date1 = DateUtil.parseMsTimestampToDate(date);
                Request requestObj  = new Request();
                requestObj.setID(requestID);
                requestObj.setSUBMITTED_BY(requestor);
                requestObj.setREQUEST_DATE(date1);
                System.out.println("ID: " + requestID + ", employeeName: " + requestor);
                requestlist.add(requestObj);
            }

            final PendingRequestsAdapter adapter = new PendingRequestsAdapter(this,requestlist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int selectedRequestID = adapter.getItem(i).getID();
                    Bundle bundle = new Bundle();
                    bundle.putInt("selectedRequestID", selectedRequestID);
                    bundle.putInt("currentUserID",currentUserID);
                    bundle.putInt("currentRoleID", currentRoleID);
                    bundle.putInt("currentDeptID", currentDeptID);
                    bundle.putInt("actingHeadID", actingHeadID);

                    Intent intent = new Intent(getApplicationContext(), DepartmentHeadRequestDecision.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerFailed() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putInt("currentUserID",currentUserID);
        bundle.putInt("currentRoleID", currentRoleID);
        bundle.putInt("currentDeptID", currentDeptID);
        bundle.putInt("actingHeadID", actingHeadID);
        Intent intent = new Intent(this, DepartmentHeadMain.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
