package com.mavpokit.rseriesalarm.addeditobject;

import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.data.source.IRepository;
import com.mavpokit.rseriesalarm.data.source.Repository;

/**
 * Created by Alex on 26.01.2017.
 */

public class AddEditObjectPresenter implements AddEditObjectContract.Presenter {

    private AddEditObjectContract.View view;
    private IRepository repository;
    String id;//if object is new, than id = null

    public AddEditObjectPresenter(AddEditObjectContract.View view, String id, IRepository repository) {
        this.view = view;
        this.repository = repository;
        this.id = id;
    }


    @Override
    public void doneClick(String name, String number, String password) {
        if (id == null)
            repository.insertObject(new AlarmObject(null, name, number, password));
        else
        {
            AlarmObject alarmObject = new AlarmObject(id, name, number, password);
            Repository.setCurrentObject(alarmObject);
            repository.updateObject(alarmObject);
        }

        view.closeAndSetResultOk();

    }
}
