package com.sibriver.testapp.sibrivertestapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sibriver.testapp.sibrivertestapp.R;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context1;
    private String[] data;
    public Resources res;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, String[] objects) {
        super(context, R.layout.spinner_row, objects);

        context1 = context;
        data = objects;

        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, R.layout.spinner_dropdown, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, R.layout.spinner_row, convertView, parent);
    }

    public View getCustomView(int position, int layout, View convertView, ViewGroup parent) {

        View row = inflater.inflate(layout, parent, false);

        TextView statusCategory = (TextView) row.findViewById(R.id.status_category);

        statusCategory.setText(data[position].toString());

        return row;
    }
}