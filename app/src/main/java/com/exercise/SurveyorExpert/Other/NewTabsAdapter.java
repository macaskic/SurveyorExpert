package com.exercise.SurveyorExpert.Other;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * NewTabsAdapter created by Calum Macaskill on 02/01/2015.
 */
public /*static*/ class NewTabsAdapter extends FragmentPagerAdapter
        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private final Context mContext ;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabsList = new ArrayList<TabInfo>();
    private int theScreenPos= 0;

        static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

    public NewTabsAdapter(FragmentActivity activity, ViewPager pager) {
        super(activity.getSupportFragmentManager());
        mContext = activity;
        mActionBar = activity.getActionBar();
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(clss, args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabsList.add(info);
        mActionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //  Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //  Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        //  Auto-generated method stub
        mActionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //  Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
     //   Toast.makeText(, "Project Fragment X", Toast.LENGTH_SHORT).show();
        Object tag = tab.getTag();
        for (int i=0; i<mTabsList.size(); i++) {
            if (mTabsList.get(i) == tag) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Auto-generated method stub
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabsList.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        /*
        Fragment temp = Fragment.instantiate(mContext, info.clss.getName(), info.args);
        return temp;
        */
    }

    @Override
    public int getCount() {
        return mTabsList.size();
    }

}
