package com.mavpokit.rseriesalarm.settings.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.ColouredEditText;
import com.mavpokit.rseriesalarm.util.ColouredSpinner;
import com.mavpokit.rseriesalarm.util.MySmsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

public class SetupPasswordFragment extends BaseSettingsFragment {

    @BindView(R.id.edittext_password)
    ColouredEditText editTextPassword;

    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupPasswordFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_password, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.set_password_button)
    void setPassword(){
        String newPassword = editTextPassword.getText().toString();
        if (newPassword.length()!=4){
            editTextPassword.setError("password must contain 4 digits!");
            return;
        }

        String smsMessage = alarmObject.getCode() + "P" + newPassword;
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);

    }
}
