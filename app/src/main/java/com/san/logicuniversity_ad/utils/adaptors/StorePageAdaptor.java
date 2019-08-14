package com.san.logicuniversity_ad.utils.adaptors;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.san.logicuniversity_ad.ui.store.fragments.StoreDisbursementContainer;
import com.san.logicuniversity_ad.ui.store.fragments.StoreRetrivalListFragment;

public class StorePageAdaptor extends FragmentPagerAdapter {

    private int numOfTabs;

    public StorePageAdaptor(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new StoreRetrivalListFragment();
            case 1:
                return new StoreDisbursementContainer();

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
