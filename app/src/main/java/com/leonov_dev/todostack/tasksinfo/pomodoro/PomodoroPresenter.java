package com.leonov_dev.todostack.tasksinfo.pomodoro;

import android.content.Context;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

@ActivityScoped
public class PomodoroPresenter implements PomodoroContract.Presenter {

    @Nullable
    private TasksRepository mTasksRepository;

    private long mTaskId;

    private Context mContext;

    private PomodoroContract.View mView;

    @Inject
    public PomodoroPresenter(long taskId, TasksRepository tasksRepository, Context context){
        mTasksRepository = tasksRepository;
        mTaskId = taskId;
        mContext = context;
    }

    @Override
    public void takeView(PomodoroContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        updateProgress();
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

    private void updateProgress(){
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                //TODO add time spent
                if (task != null) {
                    task.setTimeSpent(1);
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
        mView.setTimer(task.getTimeSpent());
    }

    @Override
    public void manageStartStopButton(String currentCaption) {
        String startCaption = mContext.getString(R.string.start_the_pomodoro);
        String stopCatpion = mContext.getString(R.string.stop_the_pomodoro);
        if (startCaption.equals(currentCaption)){
            mView.setButtonCation(stopCatpion);
        } else {
            mView.setButtonCation(startCaption);
        }
    }

    @Override
    public void updateTimeSpent() {

    }

    @Override
    public void finishTask() {

    }
}
