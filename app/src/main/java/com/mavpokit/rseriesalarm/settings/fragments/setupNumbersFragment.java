package com.mavpokit.rseriesalarm.settings.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupNumbersFragment extends BaseSettingsFragment {

    @BindView(R.id.spinner_sn)
    Spinner snSpinner;
    @BindView(R.id.spinner_f1)
    Spinner f1Spinner;
    @BindView(R.id.spinner_f2)
    Spinner f2Spinner;
    @BindView(R.id.spinner_sn_remove)
    Spinner snRemoveSpinner;


    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupNumbersFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_numbers, container, false);
        ButterKnife.bind(this,view);

        SetupSpinners();

        return view;
    }

    private void SetupSpinners() {
        ArrayAdapter<CharSequence> adapterSn = ArrayAdapter.createFromResource(getActivity(),
                R.array.serial_numbers, android.R.layout.simple_spinner_item);
        adapterSn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snSpinner.setAdapter(adapterSn);
        snRemoveSpinner.setAdapter(adapterSn);

        ArrayAdapter<CharSequence> adapterF1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.function1array, android.R.layout.simple_spinner_item);
        adapterF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        f1Spinner.setAdapter(adapterF1);

        ArrayAdapter<CharSequence> adapterF2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.function2array, android.R.layout.simple_spinner_item);
        adapterF2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        f2Spinner.setAdapter(adapterF2);



    }

}
