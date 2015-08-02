package com.sibriver.testapp.sibrivertestapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sibriver.testapp.sibrivertestapp.fragment.ListFragment;
import com.sibriver.testapp.sibrivertestapp.fragment.MapFragment;

/** Adapter class for material tabview */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    /** array of tab's names */
    CharSequence titlesOfTabs[];
    /** number of tabs in activity */
    int numberOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.titlesOfTabs = mTitles;
        this.numberOfTabs = mNumbOfTabsumb;
    }

    /** Returns Fragment which matches to it's tab position */
    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            ListFragment tab1 = new ListFragment();
            return tab1;
        }
        else{
            MapFragment tab2 = new MapFragment();
            return tab2;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesOfTabs[position];
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
