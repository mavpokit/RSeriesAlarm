package com.mavpokit.rseriesalarm.addobject;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.Injection;
import com.mavpokit.rseriesalarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddObjectActivity extends AppCompatActivity implements AddObjectContract.View {

    AddObjectContract.Presenter presenter;

    @BindView(R.id.editTextObjectName)    EditText editTextObjectName;
    @BindView(R.id.editTextDeviceNumber)    EditText editTextDeviceNumber;
    @BindView(R.id.editTextDevicePassword)    EditText editTextDevicePassword;

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

        presenter = new AddObjectPresenter(this, Injection.provideRepository(getApplicationContext()));
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
        setResult(Consts.RESULT_OK_ADD_OBJECT);
        finish();
    }


}



//Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show();
