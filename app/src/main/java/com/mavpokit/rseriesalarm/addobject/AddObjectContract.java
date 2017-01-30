package com.mavpokit.rseriesalarm.addobject;

/**
 * Created by Alex on 26.01.2017.
 */

public interface AddObjectContract {
    interface View{
        void closeAndSetResultOk();
    }
    interface Presenter{
        void doneClick(String name, String number, String password);
    }
}
