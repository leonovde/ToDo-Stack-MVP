package com.leonov_dev.todostack.tasksinfo.pomodoro;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.tasksinfo.TasksInfoContract;

public interface PomodoroContract {

    interface View extends BaseView<Presenter> {

        void setButtonCation(String caption);

        void setTitle(String title);

        void showTaskInfo();

        void startTask(long time);

        void startRest(long time);

        void stopTask();

    }


    interface Presenter extends BasePresenter<View> {

        void manageStartStopButton(String currentCaption);

        void updateProgress(int timeSpent);

        void finishTask(int timeSpent);

        //TODO decrease duration by the end of the round.

    }

}
