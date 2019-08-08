package com.san.logicuniversity_ad.ui.departmentHead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.R;

public class DepartmentHeadMain extends AppCompatActivity implements View.OnClickListener {
    Button pendingRequestsBtn;
    Button delegateHeadBtn;
    Button appointRepBtn;
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
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.pending_requests_button:
                Intent intent = new Intent(this, DepartmentHeadRequests.class);
                startActivity(intent);
        }
    }
}
