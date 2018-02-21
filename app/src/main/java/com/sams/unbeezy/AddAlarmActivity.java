package com.sams.unbeezy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sams.unbeezy.alarm.AlarmReceiver;
import com.sams.unbeezy.models.AlarmModel;

import java.util.Calendar;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AddAlarmActivity extends BaseActivity {
    String ACTIVITY_TITLE = "Add New Alarm";
    String LOG_TAG = "AddAlarmActivity";
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AddAlarmActivity inst;
    private TextView alarmTextView;

    AlarmModel newAlarm = new AlarmModel();

    public static AddAlarmActivity instance() {
        return inst;
    }

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        setToolbar(ACTIVITY_TITLE);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarm_time_picker);
        alarmTextView = (TextView) findViewById(R.id.alarm_text);

        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarm_toggle);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        boolean  alarmUp = (PendingIntent.getBroadcast(this, 0, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    long triggerTime = SystemClock.elapsedRealtime() + 10*1000;
                    long repeatInterval = 10*1000;
                    newAlarm.switchOn();

                    //If the Toggle is turned on, set the repeating alarm with a 30 second interval
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime, repeatInterval, notifyPendingIntent);

                    toastMessage = getString(R.string.alarm_on_toast);
                } else {
                    //Cancel the alarm and notification if the alarm is turned off
                    alarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();
                    newAlarm.switchOff();
                    toastMessage = getString(R.string.alarm_off_toast);
                }
                Toast.makeText(AddAlarmActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onToggleClicked(View view) {
        if(((ToggleButton) view).isChecked()) {
            Log.d(LOG_TAG, "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AddAlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AddAlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d(LOG_TAG, "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    public void onSaveButtonClicked(View view) {
        newAlarm.setHour(alarmTimePicker.getCurrentHour());
        newAlarm.setMinute(alarmTimePicker.getCurrentMinute());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newAlarm", gson.toJson(newAlarm));
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
