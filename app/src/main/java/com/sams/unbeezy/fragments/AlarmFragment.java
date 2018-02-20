package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.AddCourseActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.models.AlarmsItemModel;
import com.sams.unbeezy.models.AlarmsModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class AlarmFragment extends Fragment {
    public static final int NEW_ALARM_REQUEST = 1;
    private static String LOG_TAG = "AlarmFragment";
    AlarmsModel alarmsData;
    Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_alarm, container, false);

        // Create empty alarm model

        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "Bundle is not null");
        } else {
            Log.d(LOG_TAG, "Bundle is null");
        }

        // Insert alarm items to list of alarms
        alarmsData = new AlarmsModel();
        FloatingActionButton alarmFAB = rootView.findViewById(R.id.alarm_insert_fab);
        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddAlarmActivity.class);
                startActivityForResult(intent, NEW_ALARM_REQUEST);
            }
        });
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ALARM_REQUEST) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra(AddAlarmActivity.NEW_ALARM);
                if (result != null) {
                    Log.d(LOG_TAG, String.format("Current hour: %s", result));
                    Log.d(LOG_TAG, "New Alarm Set");
                } else {
                    Log.d(LOG_TAG, "Result is null");
                }
            }
        }

//        AlarmsItemModel alarm = (AlarmsItemModel) getArguments().getSerializable("newAlarm");
//        if (alarm != null) {
//            int h = alarm.getHour();
//            int m = alarm.getMinute();
//            Log.d(LOG_TAG, String.format("Hour: %d", h));
//            Log.d(LOG_TAG, String.format("Minute: %d", m));
//        } else {
//            alarm.setHour(1);
//            alarm.setMinute(23);
//        }
    }
}
