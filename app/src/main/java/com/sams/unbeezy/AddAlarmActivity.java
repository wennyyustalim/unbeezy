package com.sams.unbeezy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sams.unbeezy.receivers.AlarmReceiver;
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
    private TextView alarmTextView;

    AlarmModel newAlarm = new AlarmModel();

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

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
    }

    public void onToggleClicked(View view) {
        String toastMessage;

        if(((ToggleButton) view).isChecked()) {
            newAlarm.switchOn();
            toastMessage = getString(R.string.alarm_on_toast);

//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//            calendar.set(Calendar.SECOND, 0);
//
//            Intent intent = new Intent(AddAlarmActivity.this, AlarmReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(AddAlarmActivity.this, 0, intent, 0);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 10, pendingIntent);
        } else {
            newAlarm.switchOff();
            toastMessage = getString(R.string.alarm_off_toast);
//            alarmManager.cancel(pendingIntent);
//            setAlarmText("");
        }
        Toast.makeText(AddAlarmActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

//    public void setAlarmText(String alarmText) {
//        alarmTextView.setText(alarmText);
//    }

    public void onSaveButtonClicked(View view) {
        newAlarm.setHour(alarmTimePicker.getCurrentHour());
        newAlarm.setMinute(alarmTimePicker.getCurrentMinute());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newAlarm", gson.toJson(newAlarm));
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
