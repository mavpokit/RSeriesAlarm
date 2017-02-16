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
import com.mavpokit.rseriesalarm.util.ColouredEditText;
import com.mavpokit.rseriesalarm.util.ColouredSpinner;
import com.mavpokit.rseriesalarm.util.MySmsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mavpokit.rseriesalarm.Consts.ALARM_OBJECT;
import static com.mavpokit.rseriesalarm.Consts.NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupNumbersFragment extends BaseSettingsFragment {

    @BindView(R.id.spinner_serial_number)
    ColouredSpinner serialNumberSpinner;
    @BindView(R.id.editTextAuthNumber)
    ColouredEditText editTextAuthNumber;
    @BindView(R.id.spinner_f1)
    ColouredSpinner f1Spinner;
    @BindView(R.id.spinner_f2)
    ColouredSpinner f2Spinner;
    @BindView(R.id.spinner_sn_remove)
    ColouredSpinner removeSpinner;


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
        serialNumberSpinner.setAdapter(adapterSn);
        removeSpinner.setAdapter(adapterSn);

        ArrayAdapter<CharSequence> adapterF1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.function1array, android.R.layout.simple_spinner_item);
        adapterF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        f1Spinner.setAdapter(adapterF1);

        ArrayAdapter<CharSequence> adapterF2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.function2array, android.R.layout.simple_spinner_item);
        adapterF2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        f2Spinner.setAdapter(adapterF2);

    }

    @OnClick(R.id.set_auth_number_button)
    void setAuthNumberClick(){
        String number = editTextAuthNumber.getText();
        if (number.length()==0){
            editTextAuthNumber.setError(getString(R.string.error_empty));
            return;
        }
        String serialNumber = String.valueOf(serialNumberSpinner.getSelectedItemPosition()+1);
        String function1 = String.valueOf(f1Spinner.getSelectedItemPosition()+1);
        String function2 = String.valueOf(f2Spinner.getSelectedItemPosition()+1);


        String smsMessage = alarmObject.getCode() + "A" + function1 + "#" + function2 + "#" + number + "#";
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.inquire_auth_num_button)
    void inquireAuthNumbersClick(){

        String smsMessage = alarmObject.getCode() + "A" + "#";
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.remove_sn_button)
    void removeAuthNumberClick(){
        String serialNumber = String.valueOf(removeSpinner.getSelectedItemPosition()+1);

        String smsMessage = alarmObject.getCode() + serialNumber + "A" + "#";
        MySmsManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }


}
