package com.mavpokit.rseriesalarm.control;

import android.Manifest;
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
        sendSms();
    }

    @OnClick(R.id.disarm_button)
    void disarmButtonClick() {
        smsMessage = alarmObject.getCode() + "BB";
        sendSms();
    }


    @OnClick(R.id.request_status_button)
    void requstStatusButtonClick() {
        smsMessage = alarmObject.getCode() + "EE";
        sendSms();
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

    private void sendSms() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(alarmObject.getNumber(), null, smsMessage, null, null);
            Toast.makeText(this, "sending control SMS to device...", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissionWithRationale();
        }
    }

    /**
     * handles 3 cases:
     * <p> - first request after install</p>
     * <p> - request after permission was once denied</p>
     * <p> - if user set newer_ask_again</p>
     */
    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            //permission was once denied
            Snackbar sb = Snackbar.make(findViewById(R.id.root_view),
                    R.string.open_sms_permisssion_explanation,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, v -> requestSendSMS());
            showSnackBar(sb);

            SharedPreferences settings = getSharedPreferences(MY_SHARED_PREFS, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(NEVER_ASK_AGAIN, true);
            editor.commit();


        } else {
            //if user set newer_ask_again, open Settings to grant permission
            SharedPreferences settings = getSharedPreferences(MY_SHARED_PREFS, 0);
            boolean newer_ask_again = settings.getBoolean(NEVER_ASK_AGAIN, false);

            if (newer_ask_again) {
                Snackbar sb = Snackbar.make(findViewById(R.id.control_activity),
                        R.string.open_settings_explanation,
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.open, v -> openSettings());
                showSnackBar(sb);

            } else
                //first request after install
                requestSendSMS();
        }
    }

    /**
     * Increases lines in snackbar to 4 (default is 2) and shows it
     *
     * @param sb
     */
    private void showSnackBar(Snackbar sb) {
        View sbView = sb.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(4);
        sb.show();
    }

    private void requestSendSMS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUSET_CODE);
    }

    private void openSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivity(appSettingsIntent);
        //startActivityForResult(appSettingsIntent, SEND_SMS_REQUSET_CODE_FROM_SETTINGS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_REQUSET_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    Snackbar sb = Snackbar.make(findViewById(R.id.control_activity),
                            R.string.sms_denied,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, v -> {
                            });
                    showSnackBar(sb);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
