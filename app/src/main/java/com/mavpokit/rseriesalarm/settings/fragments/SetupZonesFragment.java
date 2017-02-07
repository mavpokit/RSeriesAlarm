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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

public class SetupZonesFragment extends BaseSettingsFragment {

    @BindView(R.id.spinner_zone_number)
    Spinner zoneNumberSpinner;
    @BindView(R.id.spinner_zone_attrinute)
    Spinner zoneAttrSpinner;
    @BindView(R.id.spinner_zone_number_content)
    Spinner zoneNumberContentSpinner;

    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupZonesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_zones, container, false);
        ButterKnife.bind(this,view);

        SetupSpinners();

        return view;
    }

    private void SetupSpinners() {
        ArrayAdapter<CharSequence> adapterZoneNumber = ArrayAdapter.createFromResource(getActivity(),
                R.array.zone_numbers, android.R.layout.simple_spinner_item);
        adapterZoneNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneNumberSpinner.setAdapter(adapterZoneNumber);
        zoneNumberContentSpinner.setAdapter(adapterZoneNumber);

        ArrayAdapter<CharSequence> adapterZoneAttribute = ArrayAdapter.createFromResource(getActivity(),
                R.array.zone_attribute, android.R.layout.simple_spinner_item);
        adapterZoneAttribute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneAttrSpinner.setAdapter(adapterZoneAttribute);

    }


}
