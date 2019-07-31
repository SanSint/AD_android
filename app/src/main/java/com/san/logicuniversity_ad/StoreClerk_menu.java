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


        ImageButton dis_btn=findViewById(R.id.dis_list_btn);
        if(dis_btn!=null)
            dis_btn.setOnClickListener(this);

        ImageButton retrieve_btn=findViewById(R.id.retivial_form_btn);
        if(retrieve_btn!= null)
            retrieve_btn.setOnClickListener(this);

        ImageButton stockcheck_btn=findViewById(R.id.stockcheck_btn);
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
