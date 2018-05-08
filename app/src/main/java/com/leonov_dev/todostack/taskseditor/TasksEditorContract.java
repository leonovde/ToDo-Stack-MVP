package com.leonov_dev.todostack.taskseditor;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.data.Task;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface TasksEditorContract {

    interface View extends BaseView<Presenter>{

        void showEmptyTaskError();

        void showTasksList();

        void showTaskInfo();

        void setTitle(String title);

        void setDescription(String description);

        void setReminder(String time);

        void setDuration(String duration);

        void showReminderDialog(Bundle extraData);

    }

    interface Presenter extends BasePresenter<View>{

        void insertTask(String title, String description, @Nullable String reminder,
                        @Nullable String duration);

        void populateTask();

        void populateReminder(String reminder);

        void populateDuration(String duration);

        void prepareReminderDialog(String reminderCondition);

        boolean isDataMissing();

    }


}
