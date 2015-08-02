package com.sibriver.testapp.sibrivertestapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sibriver.testapp.sibrivertestapp.R;

/** Custom adapter for spinner */
public class SpinnerAdapter extends ArrayAdapter<String> {

    /** Host-activity context */
    private Context context;
    /** Tabs names */
    private String[] data;
    /** App resources for getting colors */
    public Resources res;
    /** Inflater for Views */
    LayoutInflater inflater;

    /** Uses array of tab's names and context */
    public SpinnerAdapter(Context context, String[] objects) {
        super(context, R.layout.spinner_row, objects);

        this.context = context;
        data = objects;

        inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /** returns view for dropdown mode */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, R.layout.spinner_dropdown, convertView, parent);
    }

    /** returns view for toolbar */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, R.layout.spinner_row, convertView, parent);
    }

    /** returns row's view for different layouts */
    public View getCustomView(int position, int layout, View convertView, ViewGroup parent) {

        View row = inflater.inflate(layout, parent, false);

        TextView statusCategory = (TextView) row.findViewById(R.id.status_category);

        statusCategory.setText(data[position].toString());

        return row;
    }
}