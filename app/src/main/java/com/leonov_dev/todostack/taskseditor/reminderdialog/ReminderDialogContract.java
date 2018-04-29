package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ReminderDialogContract {

    interface View extends BaseView<Presenter> {

        void showDateTimePicker();

        void showLocationPicker();

        void setDate(String date);

        void setTime(String time);

        interface dateView extends BaseView<Presenter.datePresenter>{


        }

    }

    interface Presenter extends BasePresenter<View>{

        void setReminderDialogUiType(ReminderDialogUiType type);

        ReminderDialogUiType getReminderDialogUiType();

        void setUpUI();

        interface datePresenter extends BasePresenter<View.dateView>{

            void populateDate(String date);

            void populateTime(String time);

            void checkTimeValidity();

        }

    }
}
