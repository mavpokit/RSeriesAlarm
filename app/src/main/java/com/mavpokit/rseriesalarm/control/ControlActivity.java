package com.mavpokit.rseriesalarm.control;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.Repository;
import com.mavpokit.rseriesalarm.settings.SettingsActivity;
import com.mavpokit.rseriesalarm.util.AboutDialog;
import com.mavpokit.rseriesalarm.util.MySmsAndCallManager;

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
    @BindView(R.id.callback_button)
    Button callbackButton;
    @BindView(R.id.setup_button)
    Button settingsButton;
    @BindView(R.id.textView_device_number)
    TextView textViewDeviceNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_control);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setLogo(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlarmObject alarmObject = Repository.getCurrentObject();
        checkNotNull(alarmObject);
        getSupportActionBar().setTitle(alarmObject.getName());
        textViewDeviceNumber.setText(getString(R.string.sim_number) + " " + alarmObject.getNumber());
    }

    @OnClick(R.id.arm_button)
    void armButtonClick() {
        String smsMessage = "AA";
        MySmsAndCallManager.sendSms(this, smsMessage);
    }

    @OnClick(R.id.disarm_button)
    void disarmButtonClick() {
        String smsMessage = "BB";
        MySmsAndCallManager.sendSms(this, smsMessage);
    }

    @OnClick(R.id.request_status_button)
    void requstStatusButtonClick() {
        String smsMessage = "EE";
        MySmsAndCallManager.sendSms(this, smsMessage);
    }

    @OnClick(R.id.call_button)
    void callButtonClick() {
        MySmsAndCallManager.callPhone(this);
    }

    @OnClick(R.id.callback_button)
    void callbackButtonClick() {
        String smsMessage = "K" + "#";
        MySmsAndCallManager.sendSms(this, smsMessage);
    }


    @OnClick(R.id.setup_button)
    void settingsButtonClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        MySmsAndCallManager.clearActivityReference();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        MySmsAndCallManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            (new AboutDialog()).show(getSupportFragmentManager(),"AboutDialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


//    private void setLogo(Toolbar toolbar) {
//        if (Build.VERSION.SDK_INT >= 21)
//            toolbar.setLogo(R.drawable.ic_launcher);
//        else
//            toolbar.setLogo(R.mipmap.ic_launcher);
//    }
