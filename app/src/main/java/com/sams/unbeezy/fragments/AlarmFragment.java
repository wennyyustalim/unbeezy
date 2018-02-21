package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.models.AlarmModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class AlarmFragment extends Fragment {
    public static final int NEW_ALARM_REQUEST = 1;
    private static String LOG_TAG = "AlarmFragment";

    AlarmModel[] alarmsArray;
    LayoutInflater inflater;
    LinearLayout alarmListView;

    Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_alarm, container, false);

        FloatingActionButton alarmFAB = rootView.findViewById(R.id.alarm_insert_fab);
        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddAlarmActivity.class);
                startActivityForResult(intent, NEW_ALARM_REQUEST);
            }
        });
        alarmListView = rootView.findViewById(R.id.alarm_list);

        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ALARM_REQUEST) {
            if (resultCode == RESULT_OK) {
                String intentString = data.getStringExtra("newAlarm");
                AlarmModel newAlarm = gson.fromJson(intentString,AlarmModel.class);
                Log.d(LOG_TAG, String.format("Hour: %d", newAlarm.getHour()));
                Log.d(LOG_TAG, String.format("Minute: %d", newAlarm.getMinute()));
            }
        }

//        AlarmModel alarm = (AlarmModel) getArguments().getSerializable("newAlarm");
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
