package com.mavpokit.rseriesalarm.control;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_control);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLogo(toolbar);

        alarmObject = (AlarmObject) getIntent().getSerializableExtra(Consts.ALARM_OBJECT);
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
        String message = alarmObject.getCode() + "AA";
        sendSms(alarmObject.getNumber(), message);
    }

    @OnClick(R.id.disarm_button)
    void disarmButtonClick() {
        String message = alarmObject.getCode() + "BB";
        sendSms(alarmObject.getNumber(), message);
    }

    @OnClick(R.id.request_status_button)
    void requstStatusButtonClick() {
        String message = alarmObject.getCode() + "EE";
        sendSms(alarmObject.getNumber(), message);
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
        } else
        {
            String uri = "tel:" + alarmObject.getNumber().trim();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }

    }

    @OnClick(R.id.settings_button)
    void settingsButtonClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(Consts.ALARM_OBJECT,alarmObject);
        startActivity(intent);
    }


    private void sendSms(String number, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS permission is not granted", Toast.LENGTH_LONG).show();
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(this, "sending SMS to device...", Toast.LENGTH_SHORT).show();
        }


    }


}
