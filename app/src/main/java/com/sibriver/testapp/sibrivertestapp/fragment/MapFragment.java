package com.sibriver.testapp.sibrivertestapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibriver.testapp.sibrivertestapp.R;


/** MapFragment. It could be but you said "not needed". */
public class MapFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        return rootView;
    }
}