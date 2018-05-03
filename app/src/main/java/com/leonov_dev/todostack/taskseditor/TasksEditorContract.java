package com.leonov_dev.todostack.taskseditor;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.data.Task;

import android.support.annotation.Nullable;

public interface TasksEditorContract {

    interface View extends BaseView<Presenter>{

        void showEmptyTaskError();

        void showTasksList();

        void showTaskInfo();

        void setTitle(String title);

        void setDescription(String description);

        void setReminder(String time);

        boolean isActive();

    }

    interface Presenter extends BasePresenter<View>{

        void insertTask(String title, String description, @Nullable String Reminder);

        void populateTask();

        void populateReminder(String reminder);

        boolean isDataMissing();

    }


}
