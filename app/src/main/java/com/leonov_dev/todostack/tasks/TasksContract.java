package com.leonov_dev.todostack.tasks;

import android.support.annotation.NonNull;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void showTasks(List tasks);

        void showNoTasks();

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter<View> {

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void takeView(TasksContract.View view);

        void dropView();

    }
}
