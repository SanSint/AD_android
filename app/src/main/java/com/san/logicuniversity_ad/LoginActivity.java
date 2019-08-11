package com.san.logicuniversity_ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.san.logicuniversity_ad.ui.store.StoreClerkMainActivity;

public class  LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;

    Animation loadingAnimation = null;
    ImageView logoImage;
    LoginResult loginResult = LoginResult.LOADING;

    Boolean mShouldFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        initLoadingAnimation();

    }

    private void initLoadingAnimation() {
        logoImage = findViewById(R.id.iv_logo);
        loadingAnimation = AnimationUtils.loadAnimation(this, R.anim.jump);
        loadingAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(loginResult == LoginResult.LOADING) {
                    logoImage.startAnimation(loadingAnimation);
                } else if(loginResult == LoginResult.SUCCESSFUL) {
                    onLoginSuccessful();
                } else if (loginResult == LoginResult.UNSUCCESSFUL) {
                    onLoginUnsuccessful();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        etUsername.clearFocus();
        etPassword.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);

        btnLogin.setEnabled(false);

        loginResult = LoginResult.LOADING;
        logoImage.startAnimation(loadingAnimation);

        new AsyncWait().execute(2000);
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.equals("Sansint") && password.equals("sansint")) {
            loginResult = LoginResult.SUCCESSFUL;
        } else {
            loginResult = LoginResult.UNSUCCESSFUL;
        }
    }

    private void onLoginSuccessful() {
        Intent i = new Intent(this, StoreClerkMainActivity.class);

        ImageView logo = findViewById(R.id.iv_logo);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, logo, "transition_logo");

        startActivity(i, options.toBundle());
        mShouldFinish = true;
    }

    private void onLoginUnsuccessful() {
        btnLogin.setEnabled(true);
        Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mShouldFinish)
            finish();
    }

    class AsyncWait extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... duration) {

            try {
                Thread.sleep(duration[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            login();
        }
    }

    enum LoginResult {
        LOADING, SUCCESSFUL, UNSUCCESSFUL
    }
}
