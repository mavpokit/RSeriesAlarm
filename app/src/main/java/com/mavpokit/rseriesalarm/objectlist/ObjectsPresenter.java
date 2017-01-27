package com.mavpokit.rseriesalarm.objectlist;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.IRepository;

import java.util.List;

/**
 * Created by Alex on 24.01.2017.
 */

public class ObjectsPresenter implements ObjectsContract.Presenter {

    private ObjectsContract.View view;
    private IRepository repository;

    public ObjectsPresenter(ObjectsContract.View view, IRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
        List<AlarmObject> alarmObjects = repository.getObjects();
        if (isListNotEmpty(alarmObjects))
            view.showObjects(alarmObjects);
        else
            view.showNoObjectsText();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {

        if (requestCode==Consts.REQUEST_CODE_ADD_OBJECT && requestCode== Consts.RESULT_OK_ADD_OBJECT)
        {
            List<AlarmObject> alarmObjects = repository.getObjects();
            view.showObjects(alarmObjects);
        }

    }

    private boolean isListNotEmpty(List<AlarmObject> alarmObjects) {
        return alarmObjects!= null && alarmObjects.size()>0;
    }

}
