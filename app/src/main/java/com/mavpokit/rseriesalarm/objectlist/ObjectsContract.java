package com.mavpokit.rseriesalarm.objectlist;

import com.mavpokit.rseriesalarm.data.model.AlarmObject;

import java.util.List;

/**
 * Created by Alex on 24.01.2017.
 */

public interface ObjectsContract {
    interface View{
        void showObjects(List<AlarmObject> alarmObjects);
        void runAddObjectView();
        void openObject();
        void showNoObjectsText();

    }
    interface Presenter{
        void onCreate();
        void onActivityResult(int requestCode, int resultCode);
    }
}
