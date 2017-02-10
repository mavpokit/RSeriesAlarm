package com.mavpokit.rseriesalarm.addeditobject;

/**
 * Created by Alex on 26.01.2017.
 */

public interface AddEditObjectContract {
    interface View{
        void closeAndSetResultOk();
    }
    interface Presenter{
        void doneClick(String name, String number, String password);
    }
}
