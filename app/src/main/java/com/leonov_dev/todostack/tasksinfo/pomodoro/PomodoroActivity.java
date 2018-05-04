package com.leonov_dev.todostack.tasksinfo.pomodoro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leonov_dev.todostack.R;

import dagger.android.support.DaggerAppCompatActivity;

public class PomodoroActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
    }
}
