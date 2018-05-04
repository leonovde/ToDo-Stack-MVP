package com.leonov_dev.todostack.tasksinfo.pomodoro;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.tasksinfo.TasksInfoContract;

public interface PomodoroContract {

    interface View extends BaseView<Presenter> {

        void setTimer();
    }


    interface Presenter extends BasePresenter<View> {

        void startTimer();

    }

}
