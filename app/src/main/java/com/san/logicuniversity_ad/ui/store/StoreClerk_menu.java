package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.animation.ViewWeightAnimationWrapper;

import java.util.ArrayList;

public class StoreClerk_menu extends AppCompatActivity
        implements View.OnClickListener {

    private final int DISBURSEMENT_LIST_REQUEST_CODE = 0;
    private final int RETRIVAL_FORM_REQUEST_CODE = 1;
    private final int STOCK_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_menu);


        MaterialCardView mcDis = findViewById(R.id.mc_disbursement);
        if (mcDis != null)
            mcDis.setOnClickListener(this);

        MaterialCardView mcRet = findViewById(R.id.mc_retrival);
        if (mcRet != null)
            mcRet.setOnClickListener(this);

        MaterialCardView mcStock = findViewById(R.id.mc_stocktake);
        if (mcStock != null)
            mcStock.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mc_disbursement:
                startOpenAnimation(findViewById(R.id.cl_dis_list));
                break;

            case R.id.mc_retrival:
                startOpenAnimation(findViewById(R.id.cl_retrival_list));
                break;

            case R.id.mc_stocktake:
                startOpenAnimation(findViewById(R.id.cl_stock));
                break;
        }
    }

    // Get all children view for animation
    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);
            result.add(child);
        }
        return result;
    }

    private void startOpenAnimation(View v) {
        ViewWeightAnimationWrapper vww = new ViewWeightAnimationWrapper(v);
        ArrayList<View> allChildren = getAllChildren(v);
        for (View child : allChildren) {
            TextView childTextView = (TextView) child;
            childTextView.setVisibility(View.GONE);
        }
        ObjectAnimator animation = ObjectAnimator.ofFloat(vww, "weight", vww.getWeight(), 0);
        animation.setDuration(350);
        final View animatingView = v;
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Intent i = null;
                int requestCode = 0;
                if (animatingView == findViewById(R.id.cl_dis_list)) {
                    requestCode = DISBURSEMENT_LIST_REQUEST_CODE;
                    i = new Intent(getApplicationContext(), Store_DisbursementList.class);
                } else if (animatingView == findViewById(R.id.cl_retrival_list)) {
                    requestCode = RETRIVAL_FORM_REQUEST_CODE;
                    i = new Intent(getApplicationContext(), Store_RetrivalForm.class);
                } else if (animatingView == findViewById(R.id.cl_stock)) {
                    requestCode = STOCK_REQUEST_CODE;
                    i = new Intent(getApplicationContext(), Store_RetrivalForm.class);
                }

                if(i != null) {
                    startActivityForResult(i, requestCode);
                }

            }
        });
        animation.start();
    }

    private void startRevertAnimation(View v) {
        ViewWeightAnimationWrapper vww = new ViewWeightAnimationWrapper(v);

        ObjectAnimator animation = ObjectAnimator.ofFloat(vww, "weight", vww.getWeight(), 1);
        animation.setDuration(350);
        final View animatingView = v;
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ArrayList<View> allChildren = getAllChildren(animatingView);
                for (View child : allChildren) {
                    TextView childTextView = (TextView) child;
                    childTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        animation.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DISBURSEMENT_LIST_REQUEST_CODE) {
            startRevertAnimation(findViewById(R.id.cl_dis_list));
        } else if (requestCode == RETRIVAL_FORM_REQUEST_CODE) {
            startRevertAnimation(findViewById(R.id.cl_retrival_list));
        } else if (requestCode == STOCK_REQUEST_CODE) {
            startRevertAnimation(findViewById(R.id.cl_stock));
        }
    }



}
