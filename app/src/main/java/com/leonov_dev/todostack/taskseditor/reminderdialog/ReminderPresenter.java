package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;

import javax.inject.Inject;

public class ReminderPresenter implements ReminderDialogContract.Presenter {

    ReminderDialogContract.View mView;

    @Inject
    ReminderPresenter(){

    }

    @Override
    public void takeView(ReminderDialogContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
