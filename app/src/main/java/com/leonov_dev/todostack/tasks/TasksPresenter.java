package com.leonov_dev.todostack.tasks;

public class TasksPresenter implements TasksContract.Presenter {

//    private final TasksRepository mTasksRepository;

    private TasksContract.View mTasksView;

    @Override
    public void loadTasks(boolean forceUpdate) {

    }

    @Override
    public void addNewTask() {
        if (mTasksView != null){
            mTasksView.showAddTask();
        }
    }

    @Override
    public void takeView(TasksContract.View view) {
        this.mTasksView = view;
        loadTasks(false);
    }

    @Override
    public void dropView() {
        mTasksView = null;
    }
}
