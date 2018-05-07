package com.leonov_dev.todostack.taskseditor.durationdialog;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface DurationContract {

    interface View extends BaseView<Presenter> {

        void closeDialog(String time);

    }

    interface Presenter extends BasePresenter<View>{

        void formatTime(String hours, String minutes);

        void deleteDuration();
    }
}
