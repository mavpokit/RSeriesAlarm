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

public class SetupOtherFragment extends BaseSettingsFragment {
    @BindView(R.id.spinner_alert_first_number)
    ColouredSpinner alertFirstSpinner;
    @BindView(R.id.edittext_ext_pwr_alert)
    ColouredEditText extPwrAlertEdittext;
    @BindView(R.id.edittext_auto_report)
    ColouredEditText autoReportEdittext;

    public static BaseSettingsFragment newInstance(AlarmObject alarmObject) {
        BaseSettingsFragment fragment = new SetupOtherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ALARM_OBJECT,alarmObject);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_other, container, false);
        ButterKnife.bind(this,view);

        SetupSpinners();

        return view;
    }

    private void SetupSpinners() {
        ArrayAdapter<CharSequence> adapterAlertFirst = ArrayAdapter.createFromResource(getActivity(),
                R.array.alert_first_number, android.R.layout.simple_spinner_item);
        adapterAlertFirst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alertFirstSpinner.setAdapter(adapterAlertFirst);
    }

    @OnClick(R.id.alert_first_number_button)
    void setAuthNumberClick(){
        int alert = alertFirstSpinner.getSelectedItemPosition();
        String alertFirst = ( alert==0 ? "J" : "H" );

        String smsMessage = alarmObject.getCode() + alertFirst + "#";
        MySmsAndCallManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.ext_pwr_alert_button)
    void extPwrAlertClick(){
        String extPwrAlert = extPwrAlertEdittext.getText();
        if (extPwrAlert.length()==0){
            extPwrAlertEdittext.setError(getString(R.string.error_empty));
            return;
        }
        extPwrAlert = ((extPwrAlert.length()<2)? "0" : "") + extPwrAlert;
        String smsMessage = alarmObject.getCode() + "M" + extPwrAlert + "#";
        MySmsAndCallManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.auto_report_button)
    void autoReportClick(){
        String autoReport = autoReportEdittext.getText();
        if (autoReport.length()==0){
            autoReportEdittext.setError(getString(R.string.error_empty));
            return;
        }
        autoReport = ((autoReport.length()<2)? "0" : "") + autoReport;
        String smsMessage = alarmObject.getCode() + "T" + autoReport + "#";
        MySmsAndCallManager.sendSms(getActivity(), alarmObject.getNumber(),smsMessage);
    }

}
