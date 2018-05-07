package com.leonov_dev.todostack.taskseditor.durationdialog;

import javax.inject.Inject;

public class DurationPresenter implements DurationContract.Presenter {

    private DurationContract.View mView;

    @Inject
    public DurationPresenter(){

    }

    @Override
    public void takeView(DurationContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

}
