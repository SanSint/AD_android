package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.ui.store.fragments.StoreDisbursementContainer;
import com.san.logicuniversity_ad.ui.store.fragments.StoreRetrivalListFragment;
import com.san.logicuniversity_ad.utils.adaptors.StorePageAdaptor;

public class StoreClerkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_main);

        TabLayout storeTabLayout = findViewById(R.id.store_tabs);
        TabItem retrivalTab = findViewById(R.id.retrieval_tab);
        TabItem disbursementTab = findViewById(R.id.disbursement_tab);
        final ViewPager storeViewPager = findViewById(R.id.store_view_pager);

        // set margin between Tab items
        storeViewPager.setPageMargin(48);

        final StorePageAdaptor storePageAdaptor = new StorePageAdaptor(getSupportFragmentManager(), storeTabLayout.getTabCount());
        storeViewPager.setAdapter(storePageAdaptor);

        storeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(storeTabLayout));
        storeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                storeViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        StoreRetrivalListFragment srF = (StoreRetrivalListFragment) storePageAdaptor.getFragment(tab.getPosition());
                        srF.requestRetrivalItemList();
                        break;
                    case 1:
                        StoreDisbursementContainer sDF = (StoreDisbursementContainer) storePageAdaptor.getFragment(tab.getPosition());
                        sDF.reloadDisbursements();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void onBackPressed() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        StoreClerkMainActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit the app?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}
