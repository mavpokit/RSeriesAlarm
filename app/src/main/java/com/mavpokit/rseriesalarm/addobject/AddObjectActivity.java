package com.mavpokit.rseriesalarm.addobject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.Injection;
import com.mavpokit.rseriesalarm.R;

import butterknife.BindView;

public class AddObjectActivity extends AppCompatActivity implements AddObjectContract.View {

    AddObjectContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFab();

        presenter = new AddObjectPresenter(this, Injection.provideRepository());
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view->{
            presenter.addObject();
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
