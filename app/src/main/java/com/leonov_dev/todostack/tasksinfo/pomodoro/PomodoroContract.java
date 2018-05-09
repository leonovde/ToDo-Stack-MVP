package com.leonov_dev.todostack.tasksinfo.pomodoro;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.tasksinfo.TasksInfoContract;

public interface PomodoroContract {

    interface View extends BaseView<Presenter> {

        void setTimer(long timer);

        void setButtonCation(String caption);

        void setTitle(String title);

        void showTask();

        void startTheTask();

        void startTheBreak();

        void stopTheTask();


    }


    interface Presenter extends BasePresenter<View> {

        void manageStartStopButton(String currentCaption);

        void updateTimeSpent();

        void finishTask();

        //TODO decrease duration by the end of the round.

    }

}
