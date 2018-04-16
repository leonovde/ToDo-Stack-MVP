package com.leonov_dev.todostack.taskseditor;

import android.support.annotation.NonNull;

import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.DateConverter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public class TasksEditorPresenter implements TasksEditorContract.Presenter {

    @NonNull
    private final TasksRepository mTasksRepository;

    @Nullable
    private TasksEditorContract.View mTasksEditorView;

    @Nullable
    private long mTaskId;

    @Inject
    public TasksEditorPresenter(/*@Nullable long taskId,*/ TasksRepository tasksRepository){
//        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public void saveTask(String title, String description) {
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
            mTasksRepository.saveTask(task);
            mTasksEditorView.showTasksList();
        } else {
            mTasksEditorView.showEmptyTaskError();
        }
        //TODO show toast message to write at least description
    }

    private long getDateTime(){
        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
//        String strDate = mdformat.format(calendar.getTime());
        return calendar.getTimeInMillis();
    }

    @Override
    public void populateTask() {

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void takeTaskId(long id) {
        mTaskId = id;
    }


    @Override
    public void takeView(TasksEditorContract.View view) {
        mTasksEditorView = view;
    }

    @Override
    public void dropView() {
        mTasksEditorView = null;
    }
}
