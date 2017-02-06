package com.mavpokit.rseriesalarm.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavpokit.rseriesalarm.R;

import static com.mavpokit.rseriesalarm.Consts.NUMBER;

public class SetupOtherFragment extends BaseSettingsFragment {

    public static BaseSettingsFragment newInstance(String smsNumber) {
        BaseSettingsFragment fragment = new SetupOtherFragment();
        Bundle args = new Bundle();
        args.putString(NUMBER, smsNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_other, container, false);
    }

}
