package com.sams.unbeezy.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.models.AlarmModel;
import com.sams.unbeezy.receivers.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SchedulingService extends IntentService {
    private String LOG_TAG = "SchedulingService";

    List<AlarmModel> dataStore;
    DatabaseReference databaseReference;
    Gson gson;
//    PendingIntent pendingIntentIntent;
//    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    public SchedulingService() {
        super("SchedulingService");
        databaseReference = FirebaseDatabaseService.getInstance().child("alarms");
        gson = new Gson();
        getData();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                for (AlarmModel item : dataStore) {
                    Log.d(LOG_TAG, String.format("Hour: %d, Minute: %d", item.getHour(), item.getMinute()));
                }
                Log.d(LOG_TAG, "Service is running");
            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "No alarms in datastore");
            }
        }
    }

    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataStore = new ArrayList<>();
                if (dataSnapshot.getValue() != null) {
                    Log.d(LOG_TAG, "DataSnapshot is not null");

                    for(DataSnapshot item : dataSnapshot.getChildren()) {
                        dataStore.add(item.getValue(AlarmModel.class));
                    }
                    for (AlarmModel item : dataStore) {
                        String status;
                        if (item.isOn()) {
                            status = "ON";
//
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.setTimeInMillis(System.currentTimeMillis());
//                            calendar.set(Calendar.HOUR_OF_DAY, item.getHour());
//                            calendar.set(Calendar.MINUTE, item.getMinute());
//                            calendar.set(Calendar.SECOND, 0);
//
//                            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
//                            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
//                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                        } else {
                            status = "OFF";
                        }
                        Log.d(LOG_TAG, String.format("Hour: %d, Minute: %d, Status: %s", item.getHour(), item.getMinute(), status));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
