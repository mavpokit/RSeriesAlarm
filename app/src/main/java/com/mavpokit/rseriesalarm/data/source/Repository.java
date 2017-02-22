package com.mavpokit.rseriesalarm.data.source;

import com.mavpokit.rseriesalarm.data.model.AlarmObject;
import com.mavpokit.rseriesalarm.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 24.01.2017.
 */

public class Repository implements IRepository {

    private IRepository sqliteDatasource;

    private static Repository instance;
    private static List<AlarmObject> alarmObjects = new ArrayList<>();

    private static AlarmObject currentAlarmObject;

    private Repository(IRepository sqliteDatasource){
        this.sqliteDatasource = sqliteDatasource;
    };

    static {
//        alarmObjects.add(new AlarmObject("Квартира Сырец","777","1234"));
//        alarmObjects.add(new AlarmObject("Гараж","777","1234"));
//        alarmObjects.add(new AlarmObject("Дача","888","2345"));
//        alarmObjects.add(new AlarmObject("Квартира Осокорки","888","2345"));
    }

    public static Repository getInstance(IRepository sqliteDatasource) {
        if (instance==null) instance=new Repository(sqliteDatasource);
        return instance;
    }

    @Override
    public List<AlarmObject> getObjects() {
        Logger.log("-----repository-----: ","loading objects...");
        return sqliteDatasource.getObjects();
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertObject(AlarmObject alarmObject) {
        sqliteDatasource.insertObject(alarmObject);
    }

    @Override
    public void updateObject(AlarmObject alarmObject) {
        sqliteDatasource.updateObject(alarmObject);
    }

    @Override
    public void deleteObject(String id) {
        sqliteDatasource.deleteObject(id);
    }

    public static void setCurrentObject(AlarmObject object) {
        currentAlarmObject=object;
    }

    public static AlarmObject getCurrentObject() {
        return currentAlarmObject;
    }
}
