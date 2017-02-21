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
import com.mavpokit.rseriesalarm.data.source.Repository;

import static com.mavpokit.rseriesalarm.Consts.CALL_PHONE_REQUSET_CODE;
import static com.mavpokit.rseriesalarm.Consts.MY_SHARED_PREFS;
import static com.mavpokit.rseriesalarm.Consts.NEVER_ASK_AGAIN_SMS;
import static com.mavpokit.rseriesalarm.Consts.NEVER_ASK_AGAIN_CALL;
import static com.mavpokit.rseriesalarm.Consts.SEND_SMS_REQUSET_CODE;

/**
 * Created by Alex on 02.02.2017.
 */

public class MySmsAndCallManager {
    private static String smsNumber;
    private static String smsMessage;
    private static Activity mActivity;
    private static String mNumber;


    public static void sendSms(Activity activity, String message) {
        AlarmObject alarmObject = Repository.getCurrentObject();
        smsNumber = alarmObject.getNumber();
        smsMessage = message;
        mActivity = activity;

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(smsNumber, null, alarmObject.getCode()+smsMessage, null, null);
            Toast.makeText(mActivity, R.string.sending_control_sms, Toast.LENGTH_SHORT).show();
        } else {
            requestSmsPermissionWithRationale();
        }
    }

    public static void callPhone(Activity activity) {
        AlarmObject alarmObject = Repository.getCurrentObject();
        mNumber = alarmObject.getNumber().trim();
        mActivity = activity;

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            String uri = "tel:" + mNumber;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
//            mActivity.startActivity(intent);
        } else {
            requestCallPhonePermissionWithRationale();
        }
    }

    /**
     * handles 3 cases:
     * <p> - first request after install</p>
     * <p> - request after permission was once denied</p>
     * <p> - if user set newer_ask_again</p>
     */
    private static void requestSmsPermissionWithRationale() {
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
            editor.putBoolean(NEVER_ASK_AGAIN_SMS, true);
            editor.commit();


        } else {
            //if user set newer_ask_again, open Settings to grant permission
            SharedPreferences settings = mActivity.getSharedPreferences(MY_SHARED_PREFS, 0);
            boolean newer_ask_again = settings.getBoolean(NEVER_ASK_AGAIN_SMS, false);

            if (newer_ask_again) {
                Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                        R.string.open_settings_explanation_sms,
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.open, v -> openSettings());
                showSnackBar(sb);

            } else
                //first request after install
                requestSendSMS();
        }
    }

    /**
     * handles 3 cases:
     * <p> - first request after install</p>
     * <p> - request after permission was once denied</p>
     * <p> - if user set newer_ask_again</p>
     */
    private static void requestCallPhonePermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.CALL_PHONE)) {
            //permission was once denied
            Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                    R.string.open_call_permisssion_explanation,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, v -> requestCallPhone());
            showSnackBar(sb);

            SharedPreferences settings = mActivity.getSharedPreferences(MY_SHARED_PREFS, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(NEVER_ASK_AGAIN_CALL, true);
            editor.commit();


        } else {
            //if user set newer_ask_again, open Settings to grant permission
            SharedPreferences settings = mActivity.getSharedPreferences(MY_SHARED_PREFS, 0);
            boolean newer_ask_again = settings.getBoolean(NEVER_ASK_AGAIN_CALL, false);

            if (newer_ask_again) {
                Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                        R.string.open_settings_explanation_call,
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.open, v -> openSettings());
                showSnackBar(sb);

            } else
                //first request after install
                requestCallPhone();
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
    private static void requestCallPhone() {
        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUSET_CODE);
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
                    sendSms(mActivity, smsMessage);
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
            case CALL_PHONE_REQUSET_CODE:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(mActivity);
                } else {
                    Snackbar sb = Snackbar.make(mActivity.findViewById(R.id.root_view),
                            R.string.call_denied,
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