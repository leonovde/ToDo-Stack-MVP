package com.leonov_dev.todostack.taskseditor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.R;
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

    private Context mContext;

    @Nullable
    private long mTaskId;

    private final String LOG_TAG = TasksEditorPresenter.class.getSimpleName();

    @Inject
    public TasksEditorPresenter(long taskId, TasksRepository tasksRepository, Context context){
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mContext = context;
    }

    @Override
    public void insertTask(String title, String description, @Nullable String reminder,
                           @Nullable String duration) {
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

            //If condition received is same as default then insert null;
            String reminderCondition = mContext.getString(R.string.reminder_caption);
            if (reminderCondition.equals(reminder)){
                reminderCondition = null;
            } else {
                //TODO add date time formatter and format yyyy/MM/dd HH:mm
                reminderCondition = reminder;
            }

            //If the duration is 0:00 or 0:0 then insert 0 for the duration
            long durationTimer = 0;
            if (mContext.getString(R.string.duration_default_value).equals(duration) ||
                    "0:0".equals(duration) || "00:00".equals(duration)){
                durationTimer = 0;
            } else {
                //TODO add date time formatter and format HH:mm
                durationTimer = 0;
            }

            //New task wasn't executed
            long timeSpent = 0;

//            public long modifyDate;
//            public long mAssignedDate;
//            public String mReminderCondition;
//            public long mDuration;
//            public long mTimeSpent;

//            Task task = new Task(title, description, dateTime, dateTime);
            Task task = new Task(title, description, dateTime, dateTime,
                    reminderCondition, durationTimer, timeSpent);
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

    private long parseDuration(String duration){
        try{
            //From HH:mm to long with simple date parser
            return Long.parseLong(duration);
        } catch (Exception e){
            return 0;
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

    @Override
    public void populateDuration(String duration) {
        mTasksEditorView.setDuration(duration);
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
