package com.leonov_dev.todostack.tasksinfo;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface TasksInfoContract {

    interface View extends BaseView<Presenter>{

        void loadTask();

        void showTasksEditor();

        void showTasks();

        void showTitle(String title);

        void showDescription(String desc);
    }


    interface Presenter extends BasePresenter<View>{

        void editTask();

        void deleteTask();
    }

}
