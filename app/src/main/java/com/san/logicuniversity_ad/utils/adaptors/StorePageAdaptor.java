package com.san.logicuniversity_ad.utils.adaptors;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.san.logicuniversity_ad.ui.store.fragments.StoreDisbursementContainer;
import com.san.logicuniversity_ad.ui.store.fragments.StoreRetrivalListFragment;

import java.util.HashMap;
import java.util.Map;

public class StorePageAdaptor extends FragmentPagerAdapter {

    public Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

    private int numOfTabs;

    public StorePageAdaptor(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment fSR = new StoreRetrivalListFragment();
                mPageReferenceMap.put(position, fSR);
                return fSR;
            case 1:
                Fragment fD = new StoreDisbursementContainer();
                mPageReferenceMap.put(position, fD);
                return fD;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }
}
