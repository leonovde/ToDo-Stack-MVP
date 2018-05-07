package com.leonov_dev.todostack.tasksinfo.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
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
    private TaskCountDownTimer mTaskCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        mTaskTitle = findViewById(R.id.pomodoro_task_title);
        mProgressBar = findViewById(R.id.pomodoro_progressbar);
        mStartStopButton = findViewById(R.id.start_stop_pomodoro_button);

        mStartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bufButton = (Button) v;
                mPresenter.manageStartStopButton(bufButton.getText().toString());
            }
        });
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
    public void setTimer(long timer) {
        mProgressBar.setMax((int)timer);
        mProgressBar.setProgress((int)timer);
        mTaskCountDownTimer =
                new TaskCountDownTimer(timer,
                        1000);
        mTaskCountDownTimer.start();
    }

    @Override
    public void setButtonCation(String caption) {
        mStartStopButton.setText(caption);
    }

    @Override
    public void setTitle(String title) {
        mTaskTitle.setText(title);
    }

    @Override
    public void showTask() {
        finish();
    }


    public class TaskCountDownTimer extends CountDownTimer {

        public TaskCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int mintMills = (int) millisUntilFinished;
            mProgressBar.setProgress(mintMills);
        }

        @Override
        public void onFinish() {
            mPresenter.finishTask();
        }
    }


}
