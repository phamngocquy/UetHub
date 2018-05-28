package com.uet.qpn.uethub.MyAlarm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.uet.qpn.uethub.MainActivity;
import com.uet.qpn.uethub.R;


public class AlarmNotificationReceiver extends BroadcastReceiver {
    @SuppressLint("ResourceAsColor")
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        int idNotification = intent.getIntExtra("idNotification", -1);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,idNotification,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        String subjectName = intent.getStringExtra("subjectName");
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(subjectName)
                .setContentText("2 day left")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.icon_notifer);
            builder.setColor(R.color.status_bar_color);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(idNotification, builder.build());
        }
    }
}
