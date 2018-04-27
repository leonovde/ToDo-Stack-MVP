package com.leonov_dev.todostack.taskseditor.reminderdialog;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ReminderDialogContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View>{

    }
}
