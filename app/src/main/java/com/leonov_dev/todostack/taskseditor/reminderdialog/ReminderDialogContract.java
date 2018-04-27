package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ReminderDialogContract {

    interface View extends BaseView<Presenter> {

        void showDateTimePicker();

        void showLocationPicker();

    }

    interface Presenter extends BasePresenter<View>{

        void setReminderDialogUiType(ReminderDialogUiType type);

        ReminderDialogUiType getReminderDialogUiType();

        void setUpUI();

    }
}
