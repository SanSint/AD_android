package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentHeadDelegateHead extends AppCompatActivity implements AsyncToServer.IServerResponse, View.OnClickListener {

    private AutoCompleteTextView acEmployee;
    private TextView tvActingHeadStatus;
    private Button revokeButton;
    private Button assignButton;
    private View assignView;

    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int actingHeadID;
    private Employee selectedEmployee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_delegate_head);

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");
        actingHeadID = bundle.getInt("actingHeadID");

        acEmployee = findViewById(R.id.ac_employee_head_list);
        tvActingHeadStatus = findViewById(R.id.acting_head_status);
        revokeButton = findViewById(R.id.revoke_auth_button);
        revokeButton.setOnClickListener(this);
        assignButton = findViewById(R.id.assign_head_button);
        assignButton.setOnClickListener(this);
        assignView = findViewById(R.id.select_head_view);

        selectedEmployee = null;

    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all employees
        String endpt = BuildConfig.API_BASE_URL + "/api/getemployees/" + String.format("%d",currentDeptID);
        Command cmd = new Command(this, "get", endpt, null);
        new AsyncToServer().execute(cmd);
    }


    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("get") == 0) {
                JSONArray employeeArray = (JSONArray) jsonObj.get("result");
                //no acting head, populate list
                if (actingHeadID == 0){
                    final ArrayList<Employee> employeeList = new ArrayList<Employee>();
                    int len = employeeArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject employeeJSON = employeeArray.getJSONObject(i);
                        int employeeID = employeeJSON.getInt("ID");
                        String employeeName = employeeJSON.getString("NAME");

                        Employee employee = new Employee();
                        employee.setID(employeeID);
                        employee.setNAME(employeeName);

                        System.out.println("ID: " + employeeID + ", employeeName: " + employeeName);
                        employeeList.add(employee);
                    }

                    ArrayAdapter<Employee> adapter
                            = new ArrayAdapter<Employee>(this, android.R.layout.simple_dropdown_item_1line,
                            employeeList);
                    acEmployee.setAdapter(adapter);
                    acEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedEmployee = (Employee) adapterView.getItemAtPosition(i);
                            System.out.println(selectedEmployee.getNAME());
                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mgr.hideSoftInputFromWindow(acEmployee.getWindowToken(), 0);

                        }
                    });
                }
                //if there is acting head, display head name, show revoke button, hide appoint button
                else{

                    JSONObject actingHead = jsonObj.getJSONObject("actingHead");
                    String currentHeadName = actingHead.getString("NAME");
                    tvActingHeadStatus.setText("Current Acting Head: " + currentHeadName);
                    revokeButton.setVisibility(View.VISIBLE);
                    assignView.setVisibility(View.GONE);
                }


            }
            else if (context.compareTo("assign") == 0)
            {
                String name = selectedEmployee.getNAME();
                actingHeadID = selectedEmployee.getID();
                Bundle bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putString("appointedHead",name);
                bundle.putInt("actingHeadID", actingHeadID);
                Intent intent = new Intent(this, DepartmentHeadMain.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else if (context.compareTo("revoke") == 0)
            {
                actingHeadID = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putBoolean("revokedAuth", true);
                bundle.putInt("actingHeadID", actingHeadID);
                Intent intent = new Intent(this, DepartmentHeadMain.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putInt("currentUserID",currentUserID);
        bundle.putInt("currentRoleID", currentRoleID);
        bundle.putInt("currentDeptID", currentDeptID);
        Intent intent = new Intent(this, DepartmentHeadMain.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        JSONObject jsonObj;
        String endpt;
        Command cmd;

        switch(view.getId()){
            case R.id.assign_head_button:
                jsonObj = new JSONObject();
                try {
                    jsonObj.put("ID", selectedEmployee.getID());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                endpt = BuildConfig.API_BASE_URL + "/api/assignHead/";
                cmd = new Command(this, "assign", endpt, jsonObj);
                new AsyncToServer().execute(cmd);
                break;
            case R.id.revoke_auth_button:
                jsonObj = new JSONObject();
                try {
                    jsonObj.put("ID", actingHeadID);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                endpt = BuildConfig.API_BASE_URL + "/api/revokeAuth/";
                cmd = new Command(this, "revoke", endpt, jsonObj);
                new AsyncToServer().execute(cmd);
                break;
        }



    }
}
