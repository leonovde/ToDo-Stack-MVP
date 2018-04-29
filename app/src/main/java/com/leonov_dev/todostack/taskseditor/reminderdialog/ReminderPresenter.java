package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;

import javax.inject.Inject;

public class ReminderPresenter implements ReminderDialogContract.Presenter {

    ReminderDialogContract.View mView;

    @Inject
    ReminderPresenter(){

    }

    private ReminderDialogUiType mReminderDialogUiType;

    @Override
    public void takeView(ReminderDialogContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void setReminderDialogUiType(ReminderDialogUiType type) {
        mReminderDialogUiType = type;
    }


    @Override
    public ReminderDialogUiType getReminderDialogUiType() {
        return mReminderDialogUiType;
    }

    @Override
    public void setUpUI() {
        switch (mReminderDialogUiType){
            case DATE_TIME:
                mView.showDateTimePicker();
                break;
            case LOCATION:
                mView.showLocationPicker();
                break;
            default:
                mView.showDateTimePicker();
        }
    }

    @Override
    public void populateDialogDate(String date) {
        mView.setDate(date);
    }

    @Override
    public void populateDialogTime(String time) {

    }

    @Override
    public void checkTimeValidity() {

    }

}
