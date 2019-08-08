package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.san.logicuniversity_ad.R;

public class Store_Success extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_success);

        TextView txt=findViewById(R.id.hometv);
        txt.setOnClickListener(this);
    }



    public void onClick(View v)
    {
        Intent view=new Intent(this,StoreClerk_menu.class);
        startActivity(view);

    }
}