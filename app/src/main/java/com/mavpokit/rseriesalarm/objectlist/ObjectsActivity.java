package com.mavpokit.rseriesalarm.objectlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.Injection;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.addobject.AddObjectActivity;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObjectsActivity extends AppCompatActivity implements ObjectsContract.View {

    ObjectsContract.Presenter presenter;

    private RecyclerView objectsRecyclerView;
    private ObjectsAdapter objectsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.no_objects_textview) TextView noObjectsTextview;
    @BindView(R.id.edit_delete_textview) TextView editDeleteTextview;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.log("---ObjectsActivity---: ","onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.objectsTitle));

        initFab();
        initRecyclerView();

        presenter = new ObjectsPresenter(this,Injection.provideRepository());
        presenter.onCreate();

        Logger.log("---ObjectsActivity---: ","onCreate");
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ObjectsActivity.this, AddObjectActivity.class);
            startActivityForResult(intent, Consts.REQUEST_CODE_ADD_OBJECT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode,resultCode);

    }

    private void initRecyclerView() {
        objectsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_object);

        objectsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        objectsRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        objectsAdapter = new ObjectsAdapter();
        objectsRecyclerView.setAdapter(objectsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_object_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showObjects(List<AlarmObject> alarmObjects) {
        objectsAdapter.setAlarmObjects(alarmObjects);
        noObjectsTextview.setVisibility(View.INVISIBLE);
        editDeleteTextview.setVisibility(View.VISIBLE);
        objectsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void runAddObjectView() {

    }

    @Override
    public void openObject() {

    }

    @Override
    public void showNoObjectsText() {
        noObjectsTextview.setVisibility(View.VISIBLE);
        editDeleteTextview.setVisibility(View.INVISIBLE);
        objectsRecyclerView.setVisibility(View.INVISIBLE);
    }
}
