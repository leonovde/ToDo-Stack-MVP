package com.leonov_dev.todostack.sync;

import android.content.Context;
import android.os.Bundle;

import com.leonov_dev.todostack.utils.NotificationUtils;

public class TasksReminder {

    public static final String ACTION_SHOW_TASK = "show-task";
    public static final String ACTION_DISMISS_TASK = "dismiss-task";

    public static void executeTask(Context context, String action, Bundle extras) {
        if (ACTION_SHOW_TASK.equals(action)) {
            NotificationUtils.remindAboutTask(context, extras);
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_DISMISS_TASK.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }
    }

}
