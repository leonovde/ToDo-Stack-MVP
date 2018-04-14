package com.leonov_dev.todostack.tasksinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class TasksInfoActivity extends DaggerAppCompatActivity implements TasksInfoContract.View {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;

    public static final String TASK_ID_KEY = "taks_id";

    private final String LOG_TAG = TasksInfoActivity.class.getSimpleName();

    @Inject
    TasksInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_info);

        mTitleTextView = findViewById(R.id.task_title_text_view);
        mDescriptionTextView = findViewById(R.id.task_description_text_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        Log.e(LOG_TAG, "Resume triggered");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
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
            return true;
        } else if (id == R.id.action_delete_task){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadTask() {

    }

    @Override
    public void showTasksEditor() {

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
}
