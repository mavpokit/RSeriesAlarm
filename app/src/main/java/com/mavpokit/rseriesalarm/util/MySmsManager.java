package com.mavpokit.rseriesalarm.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;

/**
 * Created by Alex on 02.02.2017.
 */

public class MySmsManager {
    private static final int SEND_SMS_REQUSET_CODE = 1;
    private static String smsNumber;
    private static String smsMessage;

    public static void sendSms(Activity activity, String number, String message) {
        smsNumber = number;
        smsMessage = message;

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumber, null, smsMessage, null, null);
            Toast.makeText(activity, "sending control SMS to device...", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUSET_CODE);
    }

    public static void onRequestPermissionsResult(Context context, int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Consts.SEND_SMS_REQUSET_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
}
