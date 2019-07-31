package com.san.logicuniversity_ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginTemp extends AppCompatActivity {

    Button sanButton;
    Button elfieButton;
    Button elfieButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);

        sanButton = findViewById(R.id.sanButton);
        sanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreClerk_menu.class);
                startActivity(intent);
            }
        });

        elfieButton = findViewById(R.id.elfieButton);
        elfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepartmentHeadMain.class);
                startActivity(intent);
            }
        });

        elfieButton2 = findViewById(R.id.elfieButton2);
        elfieButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
    }
}
