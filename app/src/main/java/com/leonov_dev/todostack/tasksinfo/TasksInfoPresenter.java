package com.leonov_dev.todostack.tasksinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.CalendarUtils;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

@ActivityScoped
public class TasksInfoPresenter implements TasksInfoContract.Presenter {

    @Nullable
    private TasksInfoContract.View mView;

    @NonNull
    private final TasksRepository mTasksRepository;

    private long mTaskId;

    private Context mContext;

    private final String LOG_TAG = TasksInfoPresenter.class.getSimpleName();

    @Inject
    public TasksInfoPresenter(long id, TasksRepository tasksRepository, Context context){
        mTaskId = id;
        mTasksRepository = tasksRepository;
        mContext = context;
    }

    @Override
    public void editTask() {
        if (mView != null) {
            mView.showTasksEditor(mTaskId);
        }
    }

    @Override
    public void deleteTask() {
        mTasksRepository.deleteTaskById(mTaskId);
        if (mView != null){
            mView.showTaskDeleted();
        }
    }

    @Override
    public void openPomodoro() {
        if (mView != null) {
            mView.showPomodoroTimer(mTaskId);
        }
    }

    @Override
    public void takeView(TasksInfoContract.View view) {
        mView = view;
        showToDo();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    private void showToDo(){
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mView == null) {
                    return;
                }
                fillTodo(task);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void fillTodo(Task task){
        mView.showTitle(task.mTitle);
        mView.showDescription(task.mDescription);

        String reminder = task.getReminderCondition();
        //If caption isn't default then show ReminderCondition
        if (!mContext.getString(R.string.reminder_caption).equals(reminder) && reminder != null){
            mView.showReminder(reminder);
        } else {
            mView.hideReminder();
        }

        long duration = task.getDuration();
        long timeSpent = task.getTimeSpent();
        String durationValue;
        String leftOrSpentCaption;
        SimpleDateFormat formatter = CalendarUtils.getFormatForTime();
        Date time;
        if (duration > 0){
            if (duration > timeSpent){
                time = new Date(duration - timeSpent);
            } else {
                time = new Date(timeSpent);
            }
            durationValue = formatter.format(time);
            leftOrSpentCaption = mContext.getString(R.string.duration_left_time);
        } else {
            time = new Date(timeSpent);
            durationValue = formatter.format(time);
            leftOrSpentCaption = mContext.getString(R.string.duration_spent_time);
        }
        mView.showDuration(durationValue, leftOrSpentCaption);
    }
}
