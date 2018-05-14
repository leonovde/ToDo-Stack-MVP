package com.leonov_dev.todostack.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class TaskReminderIntentService  extends IntentService {

    public TaskReminderIntentService() {
        super("TaskReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        TasksReminder.executeTask(this, action, extras);
    }
}