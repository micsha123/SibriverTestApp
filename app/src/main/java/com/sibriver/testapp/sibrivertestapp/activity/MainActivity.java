package com.sibriver.testapp.sibrivertestapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sibriver.testapp.sibrivertestapp.R;
import com.sibriver.testapp.sibrivertestapp.adapter.SpinnerAdapter;
import com.sibriver.testapp.sibrivertestapp.adapter.ViewPagerAdapter;
import com.sibriver.testapp.sibrivertestapp.widget.CustomViewPager;
import com.sibriver.testapp.sibrivertestapp.widget.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinner;
    private CustomViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
    }

    public void addItemsToSpinner() {

        String[] statuses = getResources().getStringArray(R.array.status_array);

        SpinnerAdapter spinAdapter = new SpinnerAdapter(
                getApplicationContext(), statuses);

        spinner.setAdapter(spinAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                String item = adapter.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    public void blockTabs(){
        tabs.blockSlideTab();
        pager.setPagingEnabled(false);
    }

    public void unblockTabs(){
        tabs.unblockSlideTab();
        pager.setPagingEnabled(true);
    }

    public void setEnabledSpinner(boolean isEnabled){
        spinner.setEnabled(isEnabled);
    }
}