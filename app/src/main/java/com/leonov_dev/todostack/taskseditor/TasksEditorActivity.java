package com.leonov_dev.todostack.taskseditor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.tasks.TasksActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class TasksEditorActivity extends DaggerAppCompatActivity implements TasksEditorContract.View {

    @Inject
    TasksEditorContract.Presenter mPresenter;

    public static final int ADD_TASK_KEY = 1;
    public static final int EDIT_TASK_KEY = 2;

    private EditText mTitleEditText;
    private EditText mDescriptionText;
    private ActionBar mActionBar;

    private long idOfTask = -1;

    private String LOG_TAG = TasksEditorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_editor);

        // Set up the toolbar.
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.new_task_activity_title);

        mTitleEditText = findViewById(R.id.add_task_title);
        mDescriptionText = findViewById(R.id.add_task_description);

        Intent intentWithExtras = getIntent();
        if (intentWithExtras != null) {
            Bundle extras = intentWithExtras.getExtras();
            if (extras != null && extras.containsKey(getString(R.string.id_of_task_key))) {
                idOfTask = extras.getLong(getString(R.string.id_of_task_key));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        if (idOfTask != -1) {
            mPresenter.takeTaskId(idOfTask);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_new_task_menu_item){
            mPresenter.saveTask(
                    mTitleEditText.getText().toString(),
                    mDescriptionText.getText().toString());
            Log.e(LOG_TAG, "Save triggered");
            //TODO save and press back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyTaskError() {
        Toast.makeText(this,
                R.string.error_message_description_needed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList() {
        //Deprecated, feedback added onBackPressed();
        //TODO question is this ok to use static vars of another fragment ? :?
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
