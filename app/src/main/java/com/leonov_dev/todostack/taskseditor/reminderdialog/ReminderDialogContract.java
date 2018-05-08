package com.leonov_dev.todostack.taskseditor.reminderdialog;

import android.os.Bundle;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ReminderDialogContract {

    interface View extends BaseView<Presenter> {

        void showDateTimePicker();

        void showLocationPicker();

        void setDate(String date);

        void setTime(String time);

        void setLocation(String location);

        void showPickedTimeError();

        void showPickedDateError();

        void closeDialog();

        void closeDialogAndDeleteTime(String noReminderCaption);

    }

    interface Presenter extends BasePresenter<View>{

        void setReminderDialogUiType(ReminderDialogUiType type);

        ReminderDialogUiType getReminderDialogUiType();

        void setUpUI();

        void populateDialogDate(String date);

        void populateDialogTime(String time);

        void checkTimeValidity(String date, String time, String standardDate, String standardTime);

        void checkTimeValidityAndClose(String date, String time, String standardDate, String standardTime);

        void deleteReminder();

        void takeView(ReminderDialogContract.View view, Bundle extras);

    }
}
