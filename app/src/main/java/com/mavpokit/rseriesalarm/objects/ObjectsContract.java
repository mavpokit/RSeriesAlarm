package com.mavpokit.rseriesalarm.objects;

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
        void showEditDeleteDialog(AlarmObject object);

    }
    interface Presenter{
        void onCreate();
        void onActivityResult(int requestCode, int resultCode);
        void onObjectClick(AlarmObject object);
        void onDialogResult(int resultCode, AlarmObject object);
    }
}
