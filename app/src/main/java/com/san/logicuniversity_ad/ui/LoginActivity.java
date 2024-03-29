package com.san.logicuniversity_ad.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.san.logicuniversity_ad.utils.networkUtils.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.utils.networkUtils.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.ui.departmentHead.DepartmentHeadMain;
import com.san.logicuniversity_ad.ui.departmentRep.DepartmentRepMain;
import com.san.logicuniversity_ad.ui.store.StoreClerkMainActivity;

import org.json.JSONObject;

public class  LoginActivity extends AppCompatActivity implements View.OnClickListener, AsyncToServer.IServerResponse {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    String username;
    String password;

    Animation loadingAnimation = null;
    ImageView logoImage;

    LoginResult loginResult = LoginResult.LOADING;
    Bundle extraData;
    int roleId;

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

        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER))
                {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });

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
                } else if (loginResult == LoginResult.SERVERFAILED) {
                    onLoginServerFailed();
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

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("username", username);
            jsonObj.put("password", password);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //endpoint for using emulator to debug
        String endpt
                = BuildConfig.API_BASE_URL + "/api/verify";
        //endpt for using actual phone to debug
//        String endpt
//                = "http://192.168.1.116:45455/api/verify";
        Command command = new Command(this,"post",endpt,jsonObj);
        new AsyncToServer().execute(command);

//        username = null;
//        password = null;
    }

    private void onLoginSuccessful() {

        //depthead roleid = 4 ... actingdepthead roleid = 7
        if (roleId == 4){
            Intent intent = new Intent(this, DepartmentHeadMain.class);
            intent.putExtras(extraData);
            startActivity(intent);
        }
        //deptrep roleid = 6
        else if (roleId == 6 ){
            Intent intent = new Intent(this, DepartmentRepMain.class);
            intent.putExtras(extraData);
            startActivity(intent);
        }

        //store roleid = 1,2,3
        else if (roleId <= 3){
            // save username for later use
            SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", username);
            editor.commit();

            Intent intent = new Intent(this, StoreClerkMainActivity.class);
            ImageView logo = findViewById(R.id.iv_logo);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, logo, "transition_logo");
            intent.putExtras(extraData);

            startActivity(intent, options.toBundle());
        }
        mShouldFinish = true;
        username = null;
        password = null;
    }

    private void onLoginUnsuccessful() {
        btnLogin.setEnabled(true);
        Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
    }

    private void onLoginServerFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sorry!  There was something wrong with our servers. Please try again later...")
                .setTitle("Server Problem");

        AlertDialog dialog = builder.create();
        dialog.show();
        loginResult = loginResult.LOADING;
        btnLogin.setEnabled(true);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String status = (String)jsonObj.get("status");
            System.out.println("status:" + status);
            if (status.compareTo("ok") == 0){
                JSONObject userDetails = jsonObj.getJSONObject("result");
                int loggedInUserID = userDetails.getInt("ID");
                int userRoleID = userDetails.getInt("ROLE_ID");
                int userDeptID = userDetails.getInt("DEPARTMENT_ID");
                int actingHeadID = userDetails.optInt("HEAD_ID");

                System.out.println("userID: " + loggedInUserID
                        + " roleID: " + userRoleID
                        + " deptID: " + userDeptID);

                roleId = userRoleID;
                extraData = new Bundle();
                extraData.putInt("currentUserID",loggedInUserID);
                extraData.putInt("currentRoleID", userRoleID);
                extraData.putInt("currentDeptID", userDeptID);

                //depthead roleid = 4 ... actingdepthead roleid = 7
                if (userRoleID == 4){
                    extraData.putInt("actingHeadID", actingHeadID);
                }
                loginResult = LoginResult.SUCCESSFUL;
            } else {
                loginResult = LoginResult.UNSUCCESSFUL;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerFailed() {
        loginResult = LoginResult.SERVERFAILED;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mShouldFinish)
            finish();
    }

    enum LoginResult {
        LOADING, SUCCESSFUL, UNSUCCESSFUL, SERVERFAILED
    }
}
