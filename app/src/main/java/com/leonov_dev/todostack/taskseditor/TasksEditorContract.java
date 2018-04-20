package com.leonov_dev.todostack.taskseditor;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.data.Task;

public interface TasksEditorContract {

    interface View extends BaseView<Presenter>{

        void showEmptyTaskError();

        void showTasksList();

        void showTaskInfo();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();

    }

    interface Presenter extends BasePresenter<View>{

        void insertTask(String title, String description);

        void populateTask();

        boolean isDataMissing();

    }


}
