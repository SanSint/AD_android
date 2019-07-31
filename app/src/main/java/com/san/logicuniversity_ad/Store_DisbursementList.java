package com.san.logicuniversity_ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Store_DisbursementList extends AppCompatActivity
    implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_disbursementlist);

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        Intent view=new Intent(this, Store_Success.class);
        startActivity(view);

    }
}
