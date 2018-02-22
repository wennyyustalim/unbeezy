package com.sams.unbeezy.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.fragments.AlarmFragment;
import com.sams.unbeezy.lists.DismisserServicesList;
import com.sams.unbeezy.services.SchedulingService;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;

    public AlarmReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Intent received");
        Log.d("AlarmReceiver",String.format("settedClock %s",intent.getStringExtra("settedClock")));
        Boolean need = intent.getBooleanExtra("needLocation",false);
        Log.d("AlarmReceiver",String.format("%s",intent.getStringExtra("settedClock")));
        Intent intent1 = new Intent(context,PanicAttackActivity.class);
        intent1.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE,DismisserServicesList.RISE_AND_SHINE_CODE);
        context.startActivity(intent1);

//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent contentIntent = new Intent(context, PanicAttackActivity.class);
//        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_alarm_clock)
//                .setContentTitle("Panic Attack!")
//                .setContentText("Alarm is ringing")
//                .setContentIntent(contentPendingIntent)
//                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setDefaults(NotificationCompat.DEFAULT_ALL);
//
//        notificationManager.notify(NOTIFICATION_ID, builder.build());
//
//        Intent schedulingIntent = new Intent(context, SchedulingService.class);
//        context.startService(schedulingIntent);
//
//        Log.d("AlarmReceiver", "Notification built");
    }
}
