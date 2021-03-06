package com.mavpokit.rseriesalarm.objects;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.IRepository;
import com.mavpokit.rseriesalarm.data.source.Repository;

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

        if (requestCode == Consts.REQUEST_CODE_ADD_EDIT_OBJECT && resultCode == Consts.RESULT_OK_ADD_EDIT_OBJECT) {
            List<AlarmObject> alarmObjects = repository.getObjects();
            view.showObjects(alarmObjects);
        }

    }

    @Override
    public void onObjectClick(AlarmObject object) {
        Repository.setCurrentObject(object);
        view.openObject();
    }

    @Override
    public void onObjectLongClick(AlarmObject object) {
        view.showEditDeleteDialog(object);
    }

    @Override
    public void onDialogResult(int resultCode, AlarmObject object) {
        switch (resultCode) {
            case Consts.BTN_EDIT: {
                view.showEditObject(object);
                break;
            }
            case Consts.BTN_DELETE: {
                repository.deleteObject(object.getId());
                List<AlarmObject> alarmObjects = repository.getObjects();
                if (alarmObjects.size() > 0)
                    view.showObjects(alarmObjects);
                else
                    view.showNoObjectsText();
                break;
            }
        }
    }

    private boolean isListNotEmpty(List<AlarmObject> alarmObjects) {
        return alarmObjects != null && alarmObjects.size() > 0;
    }

}
