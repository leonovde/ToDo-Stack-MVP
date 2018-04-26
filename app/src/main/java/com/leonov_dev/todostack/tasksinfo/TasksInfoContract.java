package com.leonov_dev.todostack.tasksinfo;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface TasksInfoContract {

    interface View extends BaseView<Presenter>{

        void loadTask();

        void showTasksEditor(long taskId);

        void showTaskDeleted();

        void showTasks();

        void showTitle(String title);

        void showDescription(String desc);

        void showReminder(String time);
    }


    interface Presenter extends BasePresenter<View>{

        void editTask();

        void deleteTask();
    }

}
