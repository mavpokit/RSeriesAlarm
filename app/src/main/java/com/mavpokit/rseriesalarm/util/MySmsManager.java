package com.mavpokit.rseriesalarm.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;

import static com.mavpokit.rseriesalarm.Consts.MY_SHARED_PREFS;
import static com.mavpokit.rseriesalarm.Consts.NEVER_ASK_AGAIN;
import static com.mavpokit.rseriesalarm.Consts.SEND_SMS_REQUSET_CODE;

/**
 * Created by Alex on 02.02.2017.
 */

public class MySmsManager {
    private static String smsNumber;
    private static String smsMessage;
    private static Activity mActivity;

    public static void sendSms(Activity activity, String number, String message) {
        smsNumber = number;
        smsMessage = message;
        mActivity = activity;

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumber, null, smsMessage, null, null);
            Toast.makeText(mActivity, "sending control SMS to device...", Toast.LENGTH_SHORT).show();
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
    private static void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.SEND_SMS)) {
            //permission was once denied
            Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                    R.string.open_sms_permisssion_explanation,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, v -> requestSendSMS());
            showSnackBar(sb);

            SharedPreferences settings = mActivity.getSharedPreferences(MY_SHARED_PREFS, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(NEVER_ASK_AGAIN, true);
            editor.commit();


        } else {
            //if user set newer_ask_again, open Settings to grant permission
            SharedPreferences settings = mActivity.getSharedPreferences(MY_SHARED_PREFS, 0);
            boolean newer_ask_again = settings.getBoolean(NEVER_ASK_AGAIN, false);

            if (newer_ask_again) {
                Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                        R.string.open_settings_explanation,
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.open, v -> openSettings());
                showSnackBar(sb);

            } else
                //first request after install
                requestSendSMS();
        }
    }

    private static void showSnackBar(Snackbar sb) {
        View sbView = sb.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(4);
        sb.show();
    }

    private static void requestSendSMS() {
        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUSET_CODE);
    }

    private static void openSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + mActivity.getPackageName()));
        mActivity.startActivity(appSettingsIntent);
        //startActivityForResult(appSettingsIntent, SEND_SMS_REQUSET_CODE_FROM_SETTINGS);

    }

    public static void onRequestPermissionsResult(int requestCode,
                                                  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_REQUSET_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms(mActivity, smsNumber, smsMessage);
                } else {
                    Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
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

    public static void clearActivityReference() {
        if (mActivity != null) mActivity = null;
    }

}
