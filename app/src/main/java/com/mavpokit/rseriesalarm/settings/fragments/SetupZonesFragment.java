package com.mavpokit.rseriesalarm.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.ColouredEditText;
import com.mavpokit.rseriesalarm.util.ColouredSpinner;
import com.mavpokit.rseriesalarm.util.MySmsAndCallManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;

public class SetupZonesFragment extends BaseSettingsFragment {

    @BindView(R.id.spinner_zone_number)
    ColouredSpinner zoneNumberSpinner;
    @BindView(R.id.spinner_zone_attrinute)
    ColouredSpinner zoneAttrSpinner;
    @BindView(R.id.spinner_zone_number_content)
    ColouredSpinner zoneNumberContentSpinner;
    @BindView(R.id.editTextAlarmContent)
    ColouredEditText alarmContentEdittext;


    public static BaseSettingsFragment newInstance() {
        BaseSettingsFragment fragment = new SetupZonesFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ALARM_OBJECT,alarmObject);
//        fragment.setArguments(args);
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

    @OnClick(R.id.set_zone_attr_button)
    void setAuthNumberClick(){
        String zoneNumber = String.valueOf(zoneNumberSpinner.getSelectedItem());
        String attrCode = String.valueOf(zoneAttrSpinner.getSelectedItemPosition());

        String smsMessage = "D" + zoneNumber + "#" + attrCode + "#";
        MySmsAndCallManager.sendSms(getActivity(), smsMessage);
    }

    @OnClick(R.id.set_alarm_content_button)
    void setAlarmContentClick(){
        String zoneNumber = String.valueOf(zoneNumberContentSpinner.getSelectedItem());
        String alarmContent = alarmContentEdittext.getText().trim();
        if (alarmContent.length()==0){
            alarmContentEdittext.setError(getString(R.string.error_empty));
            return;
        }


        String smsMessage = "B" + zoneNumber + "#" + alarmContent + "#";
        MySmsAndCallManager.sendSms(getActivity(), smsMessage);
    }

    @OnClick(R.id.inquire_zone_attr_button)
    void inquireZoneAttrClick(){
        String smsMessage = "D" + "#";
        MySmsAndCallManager.sendSms(getActivity(), smsMessage);
    }

}
