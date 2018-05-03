package com.leonov_dev.todostack.taskseditor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.DateConverter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public class TasksEditorPresenter implements TasksEditorContract.Presenter {

    @NonNull
    private final TasksRepository mTasksRepository;

    @Nullable
    private TasksEditorContract.View mTasksEditorView;

    @Nullable
    private long mTaskId;

    private final String LOG_TAG = TasksEditorPresenter.class.getSimpleName();

    @Inject
    public TasksEditorPresenter(long taskId, TasksRepository tasksRepository){
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public void insertTask(String title, String description, @Nullable String Reminder) {
        if (!description.isEmpty()){
            if (title.isEmpty()){
                //Title is empty so lets fill it with first 20 chars of description or its length
                int lastIndexOfDescriptionForTitle = 19;
                if (description.length() < 20){
                    lastIndexOfDescriptionForTitle = description.length() - 1;
                }
                title = description.substring(0, lastIndexOfDescriptionForTitle);
            }
            long dateTime = getDateTime();
            Date readableDate = DateConverter.fromTimestamp(dateTime);
            Task task = new Task(title, description, dateTime, dateTime);

            if (mTaskId == -1){
                saveTask(task);
            } else {
                task.setId(mTaskId);
                updateTask(task);
            }
        } else {
            mTasksEditorView.showEmptyTaskError();
        }
    }

    private void saveTask(Task task){
        mTasksRepository.saveTask(task);
        mTasksEditorView.showTasksList();
    }

    private void updateTask(Task task){
        mTasksRepository.updateTask(task);
        mTasksEditorView.showTaskInfo();
    }

    private long getDateTime(){
        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
//        String strDate = mdformat.format(calendar.getTime());
        return calendar.getTimeInMillis();
    }

    @Override
    public void populateTask() {
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mTasksEditorView != null){
                    fillToDo(task);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void populateReminder(String reminder) {
        mTasksEditorView.setReminder(reminder);
    }

    public void fillToDo(Task task){
        mTasksEditorView.setTitle(task.getTitle());
        mTasksEditorView.setDescription(task.getDescription());

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void takeView(TasksEditorContract.View view) {
        mTasksEditorView = view;
        if (mTaskId != -1){
            populateTask();
        }
    }

    @Override
    public void dropView() {
        mTasksEditorView = null;
    }
}
