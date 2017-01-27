package com.mavpokit.rseriesalarm.addobject;

import com.mavpokit.rseriesalarm.data.source.IRepository;

/**
 * Created by Alex on 26.01.2017.
 */

public class AddObjectPresenter implements AddObjectContract.Presenter {

    private AddObjectContract.View view;
    private IRepository repository;

    public AddObjectPresenter(AddObjectContract.View view, IRepository repository) {
        this.view = view;
        this.repository = repository;
    }



    @Override
    public void addObject() {
        view.closeAndSetResultOk();

    }
}
