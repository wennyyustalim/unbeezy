package com.sams.unbeezy.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
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
import com.sams.unbeezy.models.SchedulesItemModel;
import com.sams.unbeezy.models.SchedulesModel;
import com.sams.unbeezy.receivers.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SchedulingService extends Service {
    private String LOG_TAG = "SchedulingService";

    List<AlarmModel> alarmDataStore;
    DatabaseReference alarmDataRef;
    List<SchedulesItemModel> schedulesDataStore;
    DatabaseReference scheduleDataRef;
    Gson gson;
    AlarmManager alarmManager;
//    PendingIntent pendingIntentIntent;
//    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    public SchedulingService() {

    }

    void updateAlarm() {
        alarmDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Alarm data received");
                alarmDataStore = new ArrayList<>();


                if(dataSnapshot.getValue() != null) {
                    for(DataSnapshot item : dataSnapshot.getChildren()) {
                        AlarmModel alarmModel = item.getValue(AlarmModel.class);
                        alarmDataStore.add(alarmModel);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, alarmModel.getHour());
                        calendar.set(Calendar.MINUTE, alarmModel.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        if(calendar.before(Calendar.getInstance())) {
                            calendar.add(Calendar.DATE, 1);
                        }

                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        intent.setAction(AlarmReceiver.ALARM_START_ACTION);
                        intent.putExtra("needLocation", false);
                        intent.putExtra("description", "Wake Up!");
                        intent.putExtra("settedClock",String.format("%d %d", alarmModel.getHour(),alarmModel.getMinute() ));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),(11100000+(alarmModel.getHour()*3600)+alarmModel.getMinute()*60),intent,0);

                        if(alarmModel.isOn()) {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                            Log.d(LOG_TAG,String.format("Set alarm for %d %d, millis: %d",alarmModel.getHour(),alarmModel.getMinute(), calendar.getTimeInMillis()));

                        } else {
                            alarmManager.cancel(pendingIntent);
                            Log.d(LOG_TAG,String.format("Cancel alarm for %d %d, millis: %d",alarmModel.getHour(),alarmModel.getMinute(), calendar.getTimeInMillis()));

                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void updateScheduleAlarm() {
        scheduleDataRef = FirebaseDatabaseService.getInstance().child("schedules");
        scheduleDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Schedules data received");
                schedulesDataStore = new ArrayList<>();
                if(dataSnapshot.getValue() != null) {
                    for(DataSnapshot i : dataSnapshot.getChildren()) {
                        for(DataSnapshot item : i.getChildren()){
                            SchedulesItemModel itemModel =item.getValue(SchedulesItemModel.class);
                            schedulesDataStore.add(itemModel);
                            int hour = Integer.parseInt(itemModel.getTime().substring(1)) + 5 ;
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            int day = 1+ Character.getNumericValue(itemModel.getTime().charAt(0));
                            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                            int settedDay = (day - weekday) %7;
                            calendar.add(Calendar.DAY_OF_YEAR, settedDay);
                            calendar.set(Calendar.HOUR_OF_DAY, hour);
                            calendar.set(Calendar.MINUTE, 30);
                            calendar.set(Calendar.SECOND, 0);
                            if(calendar.before(Calendar.getInstance())) {
                                calendar.add(Calendar.DAY_OF_YEAR, 7);
                            }

                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.setAction(AlarmReceiver.ALARM_CHECK_LOCATION);
//                            intent.putExtra("needLocation", true);
//                            intent.putExtra("description", "GO TO CAMPUS!!!!!");
//                            intent.putExtra("settedClock",String.format("day: %d hour:%d",settedDay, hour));
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),Integer.parseInt(String.format("111%d%d",day,hour)),intent,0);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                            Log.d(LOG_TAG,String.format("Set alarm for %d %d",settedDay,hour));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        gson = new Gson();
        alarmDataRef = FirebaseDatabaseService.getInstance().child("alarms");
        updateAlarm();
        updateScheduleAlarm();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
