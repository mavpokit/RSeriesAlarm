package com.mavpokit.rseriesalarm.settings.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.ColouredSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

public class SetupSirenFragment extends BaseSettingsFragment {

    @BindView(R.id.spinner_siren_responce)
    ColouredSpinner sirenResponceSpinner;


    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupSirenFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_siren, container, false);
        ButterKnife.bind(this,view);

        SetupSpinners();

        return view;
    }

    private void SetupSpinners() {
        ArrayAdapter<CharSequence> adapterSirenResponce = ArrayAdapter.createFromResource(getActivity(),
                R.array.siren_responce, android.R.layout.simple_spinner_item);
        adapterSirenResponce.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sirenResponceSpinner.setAdapter(adapterSirenResponce);


    }

}
