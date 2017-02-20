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
    @BindView(R.id.editTextDeviceNumber)    ColouredEditText editTextObjectNumber;
    @BindView(R.id.editTextDevicePassword)    ColouredEditText editTextObjectPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_add_object);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setLogo(toolbar);

        initFab();

        //if alarmObject=null, than we insert new object
        alarmObject = (AlarmObject) getIntent().getSerializableExtra(Consts.ALARM_OBJECT);
        if (alarmObject!=null) id=alarmObject.getId();

        if (alarmObject!=null) getSupportActionBar().setTitle(getString(R.string.title_activity_edit_object));

        populateEdits();

        presenter = new AddEditObjectPresenter(this, id, Injection.provideRepository(getApplicationContext()));
    }

    private void populateEdits() {
        if (alarmObject!=null){
            editTextObjectName.setText(alarmObject.getName());
            editTextObjectNumber.setText(alarmObject.getNumber());
            editTextObjectPassword.setText(alarmObject.getCode());
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
            String name = editTextObjectName.getText().toString().trim();
            String number = editTextObjectNumber.getText().toString().trim();
            String password = editTextObjectPassword.getText().toString().trim();

            if (name.equals("")){editTextObjectName.setError(getString(R.string.error_empty));return;}
            if (number.equals("")){editTextObjectNumber.setError(getString(R.string.error_empty));return;}
            if (password.length()!=4){editTextObjectPassword.setError(getString(R.string.error_password));return;}

            presenter.doneClick(name,number,password);
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
