package com.mavpokit.rseriesalarm.addeditobject;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.Injection;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.ColouredEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditObjectActivity extends AppCompatActivity implements AddEditObjectContract.View {

    AlarmObject alarmObject;
    String id;//indicates that we must update object if not null

    AddEditObjectContract.Presenter presenter;

    @BindView(R.id.editTextObjectName)    ColouredEditText editTextObjectName;
    @BindView(R.id.editTextDeviceNumber)    ColouredEditText editTextDeviceNumber;
    @BindView(R.id.editTextDevicePassword)    ColouredEditText editTextDevicePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_add_object);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLogo(toolbar);

        initFab();

        //if alarmObject=null, than we insert new object
        alarmObject = (AlarmObject) getIntent().getSerializableExtra(Consts.ALARM_OBJECT);
        if (alarmObject!=null) id=alarmObject.getId();

        populateEdits();

        presenter = new AddEditObjectPresenter(this, id, Injection.provideRepository(getApplicationContext()));
    }

    private void populateEdits() {
        if (alarmObject!=null){
            editTextObjectName.setText(alarmObject.getName());
            editTextDeviceNumber.setText(alarmObject.getNumber());
            editTextDevicePassword.setText(alarmObject.getCode());
        }
    }

    private void setLogo(Toolbar toolbar) {
        if (Build.VERSION.SDK_INT>=21)
            toolbar.setLogo(R.drawable.ic_launcher);
        else
            toolbar.setLogo(R.mipmap.ic_launcher);
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view->{
            presenter.doneClick(
                    editTextObjectName.getText().toString(),
                    editTextDeviceNumber.getText().toString(),
                    editTextDevicePassword.getText().toString()
            );
        });
    }

    @Override
    public void closeAndSetResultOk() {
        setResult(Consts.RESULT_OK_ADD_EDIT_OBJECT);
        finish();
    }


}



//Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show();
