package com.leonov_dev.todostack.tasksinfo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class TasksInfoActivity extends DaggerAppCompatActivity implements TasksInfoContract.View {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private LinearLayout mReminderLinearLayout;
    private TextView mReminderTextView;

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
        mReminderLinearLayout = findViewById(R.id.reminder_linear_layout);
        mReminderTextView = findViewById(R.id.reminder_condition);

        mReminderLinearLayout.setVisibility(View.GONE);

        // Set up the toolbar.
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.new_task_activity_title);
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
            Log.e(LOG_TAG, "Delete Button Triggered");
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
        Log.e(LOG_TAG, "We put id of task = " + taskId);
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
        Log.e(LOG_TAG, "show title Triggered, title:" + title);
    }

    @Override
    public void showDescription(String desc) {
        mDescriptionTextView.setText(desc);
    }

    @Override
    public void showReminder(String time) {
        mReminderLinearLayout.setVisibility(View.VISIBLE);
        mReminderTextView.setText(time);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
