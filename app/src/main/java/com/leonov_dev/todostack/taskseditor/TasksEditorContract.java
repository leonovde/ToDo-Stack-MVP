package com.leonov_dev.todostack.taskseditor;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface TasksEditorContract {

    interface View extends BaseView<Presenter>{

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();

    }

    interface Presenter extends BasePresenter<View>{

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();

        void takeTaskId(long id);

    }


}
