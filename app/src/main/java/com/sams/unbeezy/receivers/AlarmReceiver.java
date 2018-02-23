package com.sams.unbeezy.receivers;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sams.unbeezy.MainActivity;
import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.fragments.AlarmFragment;
import com.sams.unbeezy.helpers.GenericAPICaller;
import com.sams.unbeezy.lists.DismisserServicesList;
import com.sams.unbeezy.services.DataSyncService;
import com.sams.unbeezy.services.LocationService;
import com.sams.unbeezy.services.UnbeezyFirebaseMessagingService;

import java.util.Random;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    private String TAG = "AlarmReceiver";
    public static final String ALARM_START_ACTION = "START_PANIC_ATTACK";
    public static final String ALARM_CHECK_LOCATION = "START_CHECK_LOCATION";
    public static final String LOCATION_RECEIVED_ACTION = "LOCATION_RECEIVED";
    public static final String LOCATION_RECEIVED_ACTION_OUTSIDE_RANGE = "OUTSIDE_ITB_DETECTED";
    public static final String LOCATION_RECEIVED_ACTION_INSIDE_RANGE = "INSIDE_ITB_DETECTED";
    public static final String LOCATION_IN_RANGE_CODE = "locationInrange";
    public static final Boolean LOCATION_IN_RANGE = true;
    public static final Boolean LOCATION_OUT_RANGE = false;
    public static boolean isWaitingLocationSettingFlag = false;
    public AlarmReceiver() {
    }
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "Intent received");
        if(intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ALARM_START_ACTION)) {
                startPanicAttack(context);
            } else if (intent.getAction().equals(ALARM_CHECK_LOCATION)) {
                Log.d(TAG, "Check Location Intent Received!");
                Intent intent1 = new Intent(context, LocationService.class);
                context.startService(intent1);
                isWaitingLocationSettingFlag = true;
            } else if (intent.getAction().equals(LOCATION_RECEIVED_ACTION_INSIDE_RANGE) && isWaitingLocationSettingFlag) {
                Log.d(TAG, "Inside Range Detected!");
                isWaitingLocationSettingFlag = false;
//            createNotification(context, "Class Reminder", "Don't forget your class check in schedule", MainActivity.class);

                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            Intent intent1 = new Intent(context, DataSyncService.class);
                            intent1.setAction("PUSH_NOTIF");
                            context.startService(intent1);
                        } catch (Exception e) {

                        }

                    }
                }.run();
            } else if (intent.getAction().equals(LOCATION_RECEIVED_ACTION_OUTSIDE_RANGE) && isWaitingLocationSettingFlag) {
                Log.d(TAG, "Outside Range Detected!");
                isWaitingLocationSettingFlag = false;
                startPanicAttack(context);

            }
        }
    }

    private void startPanicAttack(Context context) {
        Intent intent1 = new Intent(context, PanicAttackActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UnbeezyPref",Context.MODE_PRIVATE);
        String code = sharedPreferences.getString("dismisserMode",null);
        intent1.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE,code);
        context.startActivity(intent1);
    }
}
