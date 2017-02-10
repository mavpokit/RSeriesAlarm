package com.mavpokit.rseriesalarm.data.source;

import com.mavpokit.rseriesalarm.data.model.AlarmObject;

import java.util.List;

/**
 * Created by Alex on 24.01.2017.
 */

public interface IRepository {
    List<AlarmObject> getObjects();
    void insertObject(AlarmObject alarmObject);
    void updateObject(AlarmObject alarmObject);
    void deleteObject(String id);

}
