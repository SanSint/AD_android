package com.san.logicuniversity_ad.ui.departmentHead;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.modals.Request;

import java.util.ArrayList;

public class DepartmentHeadRequests extends AppCompatActivity {
    private ArrayList<Request> requestlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_pending_requests);

//        ArrayList<Request> requestlist = new ArrayList<>();
//        requestlist.add(r1);
//        requestlist.add(r2);
//
//
//        ListView listView = findViewById(R.id.dept_head_requests_list);
//
//        PendingRequestsAdapter pRequestsAdapter = new PendingRequestsAdapter(this, requestlist);
//        listView.setAdapter(pRequestsAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
//        Fragment frag = new DeptHeadRequestList();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction trans = fragmentManager.beginTransaction();
//        trans.replace(R.id.requests_frag, frag);
//        trans.commit();
    }
}
