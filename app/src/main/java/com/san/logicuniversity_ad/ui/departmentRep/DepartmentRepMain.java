package com.san.logicuniversity_ad.ui.departmentRep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.LoginActivity;
import com.san.logicuniversity_ad.R;

public class DepartmentRepMain extends AppCompatActivity implements View.OnClickListener {
    Button acknowledgeBtn;

    private int currentUserID;
    private int currentRoleID;
    private int currentDeptID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_rep_main);

        acknowledgeBtn = findViewById(R.id.acknowledge_disbursement_button);
        acknowledgeBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        currentUserID = bundle.getInt("currentUserID");
        currentRoleID = bundle.getInt("currentRoleID");
        currentDeptID = bundle.getInt("currentDeptID");

        if (bundle.getString("acknowledged") != null){

            Toast.makeText(this,
                    "Successfully acknowledged disbursement. ", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onClick(View view) {

        Bundle bundle;
        Intent intent;
        switch(view.getId()){
            case R.id.acknowledge_disbursement_button:
                bundle = new Bundle();
                bundle.putInt("currentUserID",currentUserID);
                bundle.putInt("currentRoleID", currentRoleID);
                bundle.putInt("currentDeptID", currentDeptID);

                intent = new Intent(this, DepartmentRepConfirmDisbursement.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
