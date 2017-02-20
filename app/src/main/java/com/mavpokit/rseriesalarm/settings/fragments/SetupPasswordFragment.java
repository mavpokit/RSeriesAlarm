package com.mavpokit.rseriesalarm.settings.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.addeditobject.AddEditObjectActivity;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.Repository;
import com.mavpokit.rseriesalarm.util.ColouredEditText;
import com.mavpokit.rseriesalarm.util.MySmsAndCallManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;

public class SetupPasswordFragment extends BaseSettingsFragment {

    @BindView(R.id.edittext_password)
    ColouredEditText editTextPassword;

    public static BaseSettingsFragment newInstance() {
        BaseSettingsFragment fragment = new SetupPasswordFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ALARM_OBJECT,alarmObject);
//        fragment.setArguments(args);
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
            editTextPassword.setError(getString(R.string.error_password));
            return;
        }
        String smsMessage = "P" + newPassword;
        MySmsAndCallManager.sendSms(getActivity(), smsMessage);

    }

    @OnClick(R.id.launch_edit_password_button)
    void luanch_edit(){
        Intent intent = new Intent(getActivity(), AddEditObjectActivity.class);
        intent.putExtra(Consts.ALARM_OBJECT, Repository.getCurrentObject());
        startActivityForResult(intent, Consts.REQUEST_CODE_ADD_EDIT_OBJECT);
    }
}
