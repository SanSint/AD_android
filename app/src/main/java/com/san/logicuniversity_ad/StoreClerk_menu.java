package com.san.logicuniversity_ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StoreClerk_menu extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_menu);

        ImageButton btn=findViewById(R.id.retivial_from_btn);

        btn.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        Intent retrieve=new Intent(this,RetrivalForm.class);
        startActivity(retrieve);

    }
}
