package com.san.logicuniversity_ad.ui.departmentHead;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.adaptors.PendingRequestsAdapter;
import com.san.logicuniversity_ad.modals.Employee;
import com.san.logicuniversity_ad.modals.Request;
import com.san.logicuniversity_ad.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class DepartmentHeadRequests extends AppCompatActivity implements AsyncToServer.IServerResponse {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_pending_requests);

        listView = findViewById(R.id.dept_head_requests_list);

//        requestlist = getList();
//        PendingRequestsAdapter adapter = new PendingRequestsAdapter(this, requestlist);
//        listView.setAdapter(adapter);




    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all requests
        Command cmd = new Command(this, "get",
               "http://10.0.2.2:49283/home/getallrequests", null);
        new AsyncToServer().execute(cmd);
    }

    private ArrayList<Request> getList() {
//        String emp1 = new Employee();
//        emp1.setNAME("Bobbby");
//        String emp2 = new Employee();
//        emp2.setNAME("Jonie");
//        Request r1 = new Request();
//        r1.setID(4);
//        r1.setSUBMITTED_BY(emp1);
//        LocalDate dt1 = LocalDate.of(2019,8, 26);
//        r1.setREQUEST_DATE(dt1);
//        Request r2 = new Request();
//        r2.setID(5);
//        r2.setSUBMITTED_BY(emp2);
//        LocalDate dt2 = LocalDate.of(2019,5, 20);
//        r2.setREQUEST_DATE(dt2);

        ArrayList<Request> requestlist = new ArrayList<>();
//        requestlist.add(r1);
//        requestlist.add(r2);
        return requestlist;
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            JSONArray requestsArray = (JSONArray) jsonObj.get("result");
            ArrayList<Request> requestlist = new ArrayList<Request>();
            LocalDate testdate = LocalDate.of(2019, 5,4);
            for (int i = 0; i < requestsArray.length(); i++)
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

            PendingRequestsAdapter adapter = new PendingRequestsAdapter(this,requestlist);
            listView.setAdapter(adapter);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
