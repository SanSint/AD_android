package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.LoginActivity;
import com.san.logicuniversity_ad.R;

public class DepartmentHeadMain extends AppCompatActivity implements View.OnClickListener {
    Button pendingRequestsBtn;
    Button delegateHeadBtn;
    Button appointRepBtn;
    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;
    private int actingHeadID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_main);

        pendingRequestsBtn = findViewById(R.id.pending_requests_button);
        pendingRequestsBtn.setOnClickListener(this);
        delegateHeadBtn = findViewById(R.id.delegate_head_button);
        delegateHeadBtn.setOnClickListener(this);
        appointRepBtn = findViewById(R.id.change_rep_button);
        appointRepBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");
        actingHeadID = bundle.getInt("actingHeadID");
        if (bundle.getString("appointedRep") != null){
            String name = bundle.getString("appointedRep");
            Toast.makeText(this,
                    "Successfully appointed " + name + " as your department rep ", Toast.LENGTH_LONG)
                    .show();
        }
        if (actingHeadID != 0){
            //if there is an acting head, make approve request disappear
            pendingRequestsBtn.setVisibility(View.GONE);
            if (bundle.getString("appointedHead") != null){
                String name = bundle.getString("appointedHead");
                Toast.makeText(this,
                        "Successfully assigned " + name + " as your acting Head. ", Toast.LENGTH_LONG)
                        .show();
            }
        }
        else{
            if (bundle.getBoolean("revokedAuth")){
                Toast.makeText(this,
                        "No Acting Head assigned.", Toast.LENGTH_LONG)
                        .show();
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {

        Bundle bundle;
        Intent intent;
        switch(view.getId()){
            case R.id.pending_requests_button:
                bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);

                intent = new Intent(this, DepartmentHeadRequests.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.change_rep_button:
                bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);

                intent = new Intent(this, DepartmentHeadAppointRep.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.delegate_head_button:
                bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);
                bundle.putInt("actingHeadID", actingHeadID);

                intent = new Intent(this, DepartmentHeadDelegateHead.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
