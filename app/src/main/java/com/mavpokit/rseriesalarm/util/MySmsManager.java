package com.mavpokit.rseriesalarm.util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Alex on 02.02.2017.
 */

public class MySmsManager {
    public static void sendSms(Context context, String number, String message) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED)
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
        } else
            Toast.makeText(context,"SMS permission is not granted",Toast.LENGTH_LONG).show();

    }

}
