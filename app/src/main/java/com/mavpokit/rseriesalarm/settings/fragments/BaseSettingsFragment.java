package com.mavpokit.rseriesalarm.settings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.mavpokit.rseriesalarm.Consts.NUMBER;

/**
 * Created by Alex on 03.02.2017.
 */

public abstract class BaseSettingsFragment extends Fragment {
    protected String smsNumber;

    public BaseSettingsFragment() {
        // Required empty public constructor
    }

//    public static BaseSettingsFragment newInstance(String smsNumber) {
//        BaseSettingsFragment fragment = new BaseSettingsFragment();
//        Bundle args = new Bundle();
//        args.putString(NUMBER, smsNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            smsNumber = getArguments().getString(NUMBER);
        }
    }



}
