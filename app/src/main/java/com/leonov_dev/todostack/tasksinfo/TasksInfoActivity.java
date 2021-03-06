package com.leonov_dev.todostack.tasksinfo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;
import com.leonov_dev.todostack.tasksinfo.pomodoro.PomodoroActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class TasksInfoActivity extends DaggerAppCompatActivity implements TasksInfoContract.View {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private RelativeLayout mReminderRelativeLayout;
    private TextView mReminderTextView;
    private RelativeLayout mDurationRelativeLayout;
    private TextView mDurationLeftSpentTextView;
    private TextView mDurationTextView;

    private FloatingActionButton mFab;

    public static final String TASK_ID_KEY = "taks_id";

    public static final int SHOW_TASK_INFO = 10;
    public static final int SHOW_TASK_INFO_DELETED = 20;

    private final String LOG_TAG = TasksInfoActivity.class.getSimpleName();

    private ActionBar mActionBar;

    @Inject
    TasksInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_info);

        mTitleTextView = findViewById(R.id.task_title_text_view);
        mDescriptionTextView = findViewById(R.id.task_description_text_view);
        mReminderRelativeLayout = findViewById(R.id.reminder_linear_layout);
        mReminderTextView = findViewById(R.id.reminder_condition);
        mDurationRelativeLayout = findViewById(R.id.duration_linear_layout);
        mDurationLeftSpentTextView = findViewById(R.id.duration_left_spent_text_view);
        mDurationTextView = findViewById(R.id.duration_text_view);
        mFab = findViewById(R.id.start_pomodoro_fab);

        mReminderRelativeLayout.setVisibility(View.GONE);
        mDurationRelativeLayout.setVisibility(View.GONE);

        // Set up the toolbar.
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.new_task_activity_title);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openPomodoro();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_task){
            mPresenter.editTask();
            return true;
        } else if (id == R.id.action_delete_task){
            mPresenter.deleteTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadTask() {

    }

    @Override
    public void showTasksEditor(long taskId) {
        Intent intent = new Intent(this, TasksEditorActivity.class);
        intent.putExtra(TasksEditorActivity.TASK_EDIT_KEY, taskId);
        startActivity(intent);
    }

    @Override
    public void showPomodoroTimer(long taskId) {
        Intent intent = new Intent(this, PomodoroActivity.class);
        intent.putExtra(PomodoroActivity.TASK_ID_KEY, taskId);
        startActivity(intent);
    }

    @Override
    public void showTaskDeleted() {
        setResult(SHOW_TASK_INFO_DELETED);
        finish();
    }

    @Override
    public void showTasks() {

    }

    @Override
    public void showTitle(String title) {
        mTitleTextView.setText(title);
    }

    @Override
    public void showDescription(String desc) {
        mDescriptionTextView.setText(desc);
    }

    @Override
    public void showReminder(String time) {
        mReminderRelativeLayout.setVisibility(View.VISIBLE);
        mReminderTextView.setText(time);
    }

    @Override
    public void hideReminder() {
        mReminderRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void showDuration(String duration, String leftOrSpent) {
        mDurationRelativeLayout.setVisibility(View.VISIBLE);
        mDurationLeftSpentTextView.setText(leftOrSpent);
        mDurationTextView.setText(duration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
