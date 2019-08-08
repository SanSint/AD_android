package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.utils.adaptors.StorePageAdaptor;

public class StoreClerkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_main);

        TabLayout storeTabLayout = findViewById(R.id.store_tabs);
        TabItem retrivalTab = findViewById(R.id.retrieval_tab);
        TabItem disbursementTab = findViewById(R.id.disbursement_tab);
        TabItem stockTab = findViewById(R.id.stock_tab);
        final ViewPager storeViewPager = findViewById(R.id.store_view_pager);

        StorePageAdaptor storePageAdaptor = new StorePageAdaptor(getSupportFragmentManager(), storeTabLayout.getTabCount());
        storeViewPager.setAdapter(storePageAdaptor);

        storeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(storeTabLayout));
        storeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                storeViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
}
