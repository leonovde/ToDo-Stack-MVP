package com.leonov_dev.todostack.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.sync.TaskReminderIntentService;
import com.leonov_dev.todostack.sync.TasksReminder;
import com.leonov_dev.todostack.tasks.TasksActivity;

public class NotificationUtils {

    //pending intent id to uniquely reference the pending intent
    private static final int TASK_REMINDER_PENDING_INTENT_ID = 3417;

    private static final int TASK_REMINDER_NOTIFICATION_ID = 1138;

    //notification channel id to link notifications to channel
    private static final String TASK_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";


    public static void remindAboutTask(Context context, Bundle extras) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (extras == null){
            return;
        }

        long id = extras.getLong(context.getString(R.string.id_of_task_key));
        String taskTitle = extras.getString(context.getString(R.string.notification_task_title_key));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    TASK_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, TASK_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(taskTitle)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(openTaskReminderAction(context))
                .addAction(drinkWaterAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(TASK_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, TasksActivity.class);
        return PendingIntent.getActivity(
                context,
                TASK_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static NotificationCompat.Action openTaskReminderAction(Context context) {
        Intent openTaskIntent = new Intent(context, TasksActivity.class);
        PendingIntent openTask = PendingIntent.getActivity(
                context,
                TASK_REMINDER_PENDING_INTENT_ID,
                openTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action openTaskAction = new NotificationCompat.Action(
                R.drawable.ic_notification_open_action_24dp,
                "Open",
                openTask);
        return openTaskAction;
    }

    private static NotificationCompat.Action drinkWaterAction(Context context) {
        Intent ignoreTaskIntent = new Intent(context,
                TaskReminderIntentService.class);
        ignoreTaskIntent.setAction(TasksReminder.ACTION_DISMISS_TASK);
        PendingIntent ignoreTaskPendingIntent = PendingIntent.getService(
                context,
                TASK_REMINDER_PENDING_INTENT_ID,
                ignoreTaskIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action ignoreTaskAction = new NotificationCompat.Action(
                R.drawable.ic_notification_cancel_action_24dp,
                "Cancel",
                ignoreTaskPendingIntent);
        return ignoreTaskAction;
    }

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground);
        return largeIcon;
    }
}
