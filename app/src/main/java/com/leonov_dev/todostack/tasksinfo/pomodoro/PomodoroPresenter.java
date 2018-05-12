package com.leonov_dev.todostack.tasksinfo.pomodoro;

import android.content.Context;
import android.util.Log;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.CalendarUtils;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

@ActivityScoped
public class PomodoroPresenter implements PomodoroContract.Presenter {

    private static final String LOG_TAG = PomodoroPresenter.class.getSimpleName();
    @Nullable
    private TasksRepository mTasksRepository;

    private long mTaskId;
    private long mTaskTime;

    private Context mContext;

    private PomodoroContract.View mView;

    //TODO to be received from shared preferences
    private final long TWENTY_FIVE_MIN = 1500000;
    private final long FIVE_MIN = 300000;

    @Inject
    public PomodoroPresenter(long taskId, TasksRepository tasksRepository, Context context){
        mTasksRepository = tasksRepository;
        mTaskId = taskId;
        mContext = context;
    }

    @Override
    public void takeView(PomodoroContract.View view) {
        mView = view;
        showToDo();
    }

    @Override
    public void dropView() {
        //TODO add time spent
        mView = null;
    }

    private void showToDo(){
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mView == null) {
                    return;
                }
                mTaskTime = task.getDuration();
                fillTodo(task);
            }
            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void updateProgress(final int timeSpent){
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                //TODO add time spent
                if (task != null) {
                    task.setTimeSpent((long)timeSpent);
                    mTasksRepository.updateTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void fillTodo(Task task){
        mView.setTitle(task.getTitle());
        mView.setButtonCation(mContext.getString(R.string.start_the_pomodoro));
    }

    @Override
    public void manageStartStopButton(String currentCaption) {
        String startCaption = mContext.getString(R.string.start_the_pomodoro);
        String stopCatpion = mContext.getString(R.string.stop_the_pomodoro);
        String startRestCaption = mContext.getString(R.string.start_the_rest_pomodoro);
        if (startCaption.equals(currentCaption)){
            mView.setButtonCation(stopCatpion);
            if (mTaskTime < TWENTY_FIVE_MIN){
                mView.startTask(mTaskTime);
            } else {
                mView.startTask(TWENTY_FIVE_MIN);
            }
        } else if (stopCatpion.equals(currentCaption)) {
            mView.setButtonCation(startCaption);
            mView.stopTask();
        } else if (startRestCaption.equals(currentCaption)) {
            mView.setButtonCation(stopCatpion);
            mView.startRest(FIVE_MIN);
        }
    }


    @Override
    public void finishTask(int timeSpent) {
        updateProgress(timeSpent);
        mView.setButtonCation(mContext.getString(R.string.start_the_rest_pomodoro));
    }
}
