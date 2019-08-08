package com.san.logicuniversity_ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.san.logicuniversity_ad.ui.store.StoreClerkMainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        Log.i("LOGIN", username);
        Log.i("LOGIN", password);

        etUsername.clearFocus();
        etPassword.clearFocus();

        if (username.equals("Sansint") && password.equals("sansint")) {

            Intent i = new Intent(this, StoreClerkMainActivity.class);

            ImageView logo = findViewById(R.id.iv_logo);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, logo, "transition_logo");

            startActivity(i, options.toBundle());
//        finish();
        } else {
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
