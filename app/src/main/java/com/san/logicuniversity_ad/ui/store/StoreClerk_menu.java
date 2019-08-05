package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.san.logicuniversity_ad.R;

public class StoreClerk_menu extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_menu);


        Button dis_btn=findViewById(R.id.dis_list_btn);
        if(dis_btn!=null)
            dis_btn.setOnClickListener(this);

        Button retrieve_btn=findViewById(R.id.retivial_form_btn);
        if(retrieve_btn!= null)
            retrieve_btn.setOnClickListener(this);

        Button stockcheck_btn=findViewById(R.id.stockcheck_btn);
        if(stockcheck_btn!=null)
            stockcheck_btn.setOnClickListener(this);


    }
    public void onClick(View v)
    {
        Intent intent=null;

        switch(v.getId())
        {
            case R.id.dis_list_btn:
                intent =new Intent(this, Store_DisbursementList.class);
                break;


            case R.id.retivial_form_btn:
                intent =new Intent(this, Store_RetrivalForm.class);
                break;



            case R.id.stockcheck_btn:
                intent =new Intent(this, Store_StockCheckList.class);
                break;
        }
     if (intent!=null)
         startActivity(intent);

    }
}
