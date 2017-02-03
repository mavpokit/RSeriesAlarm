package com.mavpokit.rseriesalarm.control;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.addobject.AddObjectContract;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.settings.SettingsActivity;
import com.mavpokit.rseriesalarm.util.MySmsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.mavpokit.rseriesalarm.Consts.MY_SHARED_PREFS;
import static com.mavpokit.rseriesalarm.Consts.NEVER_ASK_AGAIN;
import static com.mavpokit.rseriesalarm.Consts.SEND_SMS_REQUSET_CODE;

public class ControlActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.arm_button)
    Button armButton;
    @BindView(R.id.disarm_button)
    Button disarmButton;
    @BindView(R.id.request_status_button)
    Button statusButton;
    @BindView(R.id.call_button)
    Button callButton;
    @BindView(R.id.settings_button)
    Button settingsButton;

    AlarmObject alarmObject;

    private String smsNumber;
    private String smsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);

        alarmObject = (AlarmObject) getIntent().getSerializableExtra(Consts.ALARM_OBJECT);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_control);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLogo(toolbar);
        checkNotNull(alarmObject);
        getSupportActionBar().setTitle(alarmObject.getName());
    }

    private void setLogo(Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setLogo(R.drawable.ic_launcher);
        else
            toolbar.setLogo(R.mipmap.ic_launcher);
    }

    @OnClick(R.id.arm_button)
    void armButtonClick() {
        smsMessage = alarmObject.getCode() + "AA";
        MySmsManager.sendSms(this, alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.disarm_button)
    void disarmButtonClick() {
        smsMessage = alarmObject.getCode() + "BB";
        MySmsManager.sendSms(this, alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.request_status_button)
    void requstStatusButtonClick() {
        smsMessage = alarmObject.getCode() + "EE";
        MySmsManager.sendSms(this, alarmObject.getNumber(),smsMessage);
    }

    @OnClick(R.id.call_button)
    void callButtonClick() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Call permission is not granted", Toast.LENGTH_LONG).show();
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            String uri = "tel:" + alarmObject.getNumber().trim();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }

    }

    @OnClick(R.id.settings_button)
    void settingsButtonClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(Consts.ALARM_OBJECT, alarmObject);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        MySmsManager.clearActivityReference();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        MySmsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
