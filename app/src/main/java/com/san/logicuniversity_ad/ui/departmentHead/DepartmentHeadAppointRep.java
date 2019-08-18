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

import com.san.logicuniversity_ad.utils.networkUtils.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.utils.networkUtils.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentHeadAppointRep extends AppCompatActivity implements AsyncToServer.IServerResponse, View.OnClickListener {

    private AutoCompleteTextView acEmployee;
    private TextView tvCurrentEmployee;
    private Button appointButton;
    private TextView tvErrorMessage;

    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int actingHeadID;
    private Employee selectedEmployee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_appoint_rep);

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");
        actingHeadID = bundle.getInt("actingHeadID");

        acEmployee = findViewById(R.id.ac_employee_list);
        tvCurrentEmployee = findViewById(R.id.dept_rep);
        tvErrorMessage = findViewById(R.id.appoint_error_msg);
        appointButton = findViewById(R.id.appoint_rep_button);
        appointButton.setOnClickListener(this);

        selectedEmployee = null;

    }

    @Override
    protected void onStart() {
        super.onStart();
        //async task to get all employees
        String endpt = BuildConfig.API_BASE_URL+ "/api/getemployees/" + String.format("%d",currentDeptID);
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
                JSONObject rep = jsonObj.getJSONObject("rep");
                String currentRepName = rep.getString("NAME");
                final ArrayList<Employee> employeeList = new ArrayList<Employee>();
                tvCurrentEmployee.setText(currentRepName);

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
            else if (context.compareTo("appoint") == 0)
            {
                String name = selectedEmployee.getNAME();
                Bundle bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putInt("actingHeadID", actingHeadID);
                bundle.putString("appointedRep",name);
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


    @Override
    public void onClick(View view) {
        if(selectedEmployee == null){
                tvErrorMessage.setText("Please enter a valid employee name.");
        }
        else{
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("ID", selectedEmployee.getID());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            String endpt = BuildConfig.API_BASE_URL + "/api/updateRep/";
            Command cmd = new Command(this, "appoint", endpt, jsonObj);
            new AsyncToServer().execute(cmd);

        }
    }
}
