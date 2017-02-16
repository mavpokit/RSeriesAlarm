package com.mavpokit.rseriesalarm.settings.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.ColouredEditText;
import com.mavpokit.rseriesalarm.util.MySmsManager;

import butterknife.BindView;
import butterknife.OnClick;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

public class SetupDelaysFragment extends BaseSettingsFragment {

    @BindView(R.id.edittext_alarm_delay)
    ColouredEditText alarmDelayEdittext;
    @BindView(R.id.edittext_arm_delay)
    ColouredEditText armDelayEdittext;


    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupDelaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_delays, container, false);
    }


    @OnClick(R.id.set_alarm_delay_button)
    void setAlarmContentClick(){
        String alarmDelay = alarmDelayEdittext.getText();
        if (alarmDelay.length()==0){
            alarmDelayEdittext.setError(getString(R.string.error_empty));
            return;
        }
        alarmDelay = ((alarmDelay.length()<2)? "0" : "") + alarmDelay;
        alarmDelay = ((alarmDelay.length()<3)? "0" : "") + alarmDelay;
        String smsMessage = alarmObject.getCode() + "F" + alarmDelay + "#";
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.set_arm_delay_button)
    void setArmContentClick(){
        String armDelay = armDelayEdittext.getText();
        if (armDelay.length()==0){
            armDelayEdittext.setError(getString(R.string.error_empty));
            return;
        }
        armDelay = ((armDelay.length()<2)? "0" : "") + armDelay;
        String smsMessage = alarmObject.getCode() + "G" + armDelay + "#";
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }
}
