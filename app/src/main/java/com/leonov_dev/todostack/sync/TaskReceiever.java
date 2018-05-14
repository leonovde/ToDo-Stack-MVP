package com.leonov_dev.todostack.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TaskReceiever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TasksEditorPresenter", "Received");
        Log.e("TasksEditorPresenter", "Received");
        Intent unwrappedIntent = new Intent(context, TaskReminderIntentService.class);
        unwrappedIntent.setAction(TasksReminder.ACTION_SHOW_TASK);
        unwrappedIntent.putExtras(intent.getExtras());
        context.startService(unwrappedIntent);
    }
}
