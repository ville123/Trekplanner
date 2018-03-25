package com.trekplanner.app.fragment.listable.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for tabs
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> tabContents;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return tabContents.get(position);
    }

    @Override
    public int getCount() {
        return this.tabContents.size();
    }

    public void addTabContent(Fragment fragment) {
        if (this.tabContents == null) this.tabContents = new ArrayList<>();
        this.tabContents.add(fragment);
    }
}
