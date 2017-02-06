package com.mavpokit.rseriesalarm.settings.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavpokit.rseriesalarm.R;

import static com.mavpokit.rseriesalarm.Consts.NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupNumbersFragment extends BaseSettingsFragment {

    public static BaseSettingsFragment newInstance(String smsNumber) {
        BaseSettingsFragment fragment = new SetupNumbersFragment();
        Bundle args = new Bundle();
        args.putString(NUMBER, smsNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_numbers, container, false);
    }

}
