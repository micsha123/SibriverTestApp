package com.sibriver.testapp.sibrivertestapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.sibriver.testapp.sibrivertestapp.R;
import com.sibriver.testapp.sibrivertestapp.adapter.SpinnerAdapter;
import com.sibriver.testapp.sibrivertestapp.adapter.ViewPagerAdapter;
import com.sibriver.testapp.sibrivertestapp.widget.CustomViewPager;
import com.sibriver.testapp.sibrivertestapp.widget.SlidingTabLayout;

/**
 * Main and the one activity in app
 */
public class MainActivity extends AppCompatActivity {

    /** Toolbar of application */
    private Toolbar toolbar;
    /** Spinner of application with list of statuses */
    private Spinner spinner;
    /** ViewPager with block/unblock method
     * @see CustomViewPager
     * */
    private CustomViewPager pager;
    /** ViewPager for making material tabs
     * @see ViewPagerAdapter
     * */
    private ViewPagerAdapter adapter;
    /** Material tabs
     * @see SlidingTabLayout
     * */
    private SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** All names and quantity of statuses*/
        String titlesOfTabs[] = getResources().getStringArray(R.array.tabs_array);
        int numberOfTabs = titlesOfTabs.length;

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        spinner = (Spinner) findViewById(R.id.spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addItemsToSpinner();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titlesOfTabs, numberOfTabs);

        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        /** Colorizing of tabs */
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
    }

    /** Inflating spinner with names */
    public void addItemsToSpinner() {

        String[] statuses = getResources().getStringArray(R.array.status_array);

        SpinnerAdapter spinAdapter = new SpinnerAdapter(
                this, statuses);

        spinner.setAdapter(spinAdapter);
    }

    /** Returns spinner for using it in Fragments */
    public Spinner getSpinner(){
        return spinner;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Blocks tabs while CAB of ListFragment is enabled */
    public void blockTabs(){
        tabs.blockSlideTab();
        pager.setPagingEnabled(false);
    }

    /** Unblocks tabs while CAB of ListFragment is disabled */
    public void unblockTabs(){
        tabs.unblockSlideTab();
        pager.setPagingEnabled(true);
    }

    /** Blocks spinner while CAB of ListFragment is enabled */
    public void setEnabledSpinner(boolean isEnabled){
        spinner.setEnabled(isEnabled);
    }
}