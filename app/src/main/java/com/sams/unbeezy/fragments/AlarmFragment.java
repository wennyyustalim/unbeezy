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
import android.widget.TextView;

import com.google.gson.Gson;
import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.models.AlarmModel;

import java.util.List;

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

    public void updateLayout(final List<AlarmModel> alarmsArray) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adaptLinearLayout(alarmListView, alarmsArray);
            }
        });
    }

    private void adaptLinearLayout(LinearLayout layout, List<AlarmModel> alarmsArray) {
        layout.removeAllViews();
        Log.d("NEWADAPTOR", gson.toJson(alarmsArray));
        int height = 0;
        for (AlarmModel item : alarmsArray) {
            View inflated = inflateLayout(item, layout);
            layout.addView(inflated);
            height += inflated.getMeasuredHeight();
        }
        layout.getLayoutParams().height = height;
    }

    private View inflateLayout(AlarmModel model, ViewGroup parent) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View inflated = inflater.inflate(R.layout.component_alarms_list_view, parent, false);
        TextView alarmTime = inflated.findViewById(R.id.alarm_time);
        alarmTime.setText(String.format("%d:%d", model.getHour(), model.getMinute()));

        return inflated;
    }
}
