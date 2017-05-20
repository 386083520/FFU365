package com.example.administrator.ffu365.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19.
 */

public class HomePagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> mFragments;


    public HomePagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
