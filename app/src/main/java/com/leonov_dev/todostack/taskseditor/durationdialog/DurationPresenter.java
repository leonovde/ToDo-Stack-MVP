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

    //TODO change to formatter or regular expression
    @Override
    public void formatTime(String hours, String minutes) {
        String formHours;
        String formMins;
        if (hours.length() <= 1){
            formHours = "0" + hours;
        } else {
            formHours = hours;
        }
        if (minutes.length() <= 1){
            formMins = "0" + minutes;
        } else {
            formMins = minutes;
        }
        mView.closeDialog(formHours + ":" + formMins);
    }
}
