package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ReminderDialogContract {

    interface View extends BaseView<Presenter> {

        void showDateTimePicker();

        void showLocationPicker();

        void setDate(String date);

        void setTime(String time);

        void showPickedTimeError();

        void showPickedDateError();

    }

    interface Presenter extends BasePresenter<View>{

        void setReminderDialogUiType(ReminderDialogUiType type);

        ReminderDialogUiType getReminderDialogUiType();

        void setUpUI();

        void populateDialogDate(String date);

        void populateDialogTime(String time);

        void checkTimeValidity(String date, String time, String standardDate, String standardTime);

    }
}
