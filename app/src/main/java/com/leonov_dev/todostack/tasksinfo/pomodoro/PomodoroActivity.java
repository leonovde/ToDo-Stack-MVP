package com.leonov_dev.todostack.tasksinfo.pomodoro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leonov_dev.todostack.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class PomodoroActivity extends DaggerAppCompatActivity implements PomodoroContract.View {

    public static final String TASK_ID_KEY = "task_id";

    @Inject
    public PomodoroContract.Presenter mPresenter;

    private Button mStartStopButton;
    private ProgressBar mProgressBar;
    private TextView mTaskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setTimer() {

    }


}
