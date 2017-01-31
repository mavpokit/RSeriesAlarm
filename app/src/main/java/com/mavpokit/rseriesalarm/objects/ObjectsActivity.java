package com.mavpokit.rseriesalarm.objects;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.Injection;
import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.addobject.AddObjectActivity;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.InfoView;
import com.mavpokit.rseriesalarm.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObjectsActivity extends AppCompatActivity implements ObjectsContract.View {

    ObjectsContract.Presenter presenter;

    private RecyclerView objectsRecyclerView;
    private ObjectsAdapter objectsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.no_objects_infoview) InfoView noObjectsInfoview;
    @BindView(R.id.edit_delete_infoview) InfoView editDeleteInfoview;


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
        setLogo(toolbar);


        initFab();
        initRecyclerView();

        presenter = new ObjectsPresenter(this,Injection.provideRepository(getApplicationContext()));
        presenter.onCreate();

        Logger.log("---ObjectsActivity---: ","onCreate");
    }

    private void setLogo(Toolbar toolbar) {
        if (Build.VERSION.SDK_INT>=21)
            toolbar.setLogo(R.drawable.ic_launcher);
        else
            toolbar.setLogo(R.mipmap.ic_launcher);
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
        Logger.log("---ObjectsActivity---: ","onActivityResult");

    }

    private void initRecyclerView() {
        objectsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_object);

        objectsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        objectsRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        objectsAdapter = new ObjectsAdapter(object -> presenter.onObjectClick(object));
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
        noObjectsInfoview.setVisibility(View.INVISIBLE);
        editDeleteInfoview.setVisibility(View.VISIBLE);
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
        noObjectsInfoview.setVisibility(View.VISIBLE);
        editDeleteInfoview.setVisibility(View.INVISIBLE);
        objectsRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEditDeleteDialog(AlarmObject object) {
        EditDeleteDialog dialog = new EditDeleteDialog();
        //object name is title of dialog
        Bundle bundle = new Bundle();
        bundle.putString(Consts.NAME, object.getName());
        dialog.setArguments(bundle);
        dialog.setListener(resultCode -> presenter.onDialogResult(resultCode,object));
        dialog.show(getSupportFragmentManager(),"EditDeleteDialog");
    }
}
