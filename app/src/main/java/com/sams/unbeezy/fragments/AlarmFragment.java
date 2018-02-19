package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.AddCourseActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.models.AlarmsItemModel;
import com.sams.unbeezy.models.AlarmsModel;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class AlarmFragment extends Fragment {
    AlarmsModel alarmsData;
    Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_alarm, container, false);
        alarmsData = new AlarmsModel();
        AlarmsItemModel alarmsItemModel = new AlarmsItemModel();
        alarmsItemModel.setHour(1);
        alarmsItemModel.setMinute(23);
        alarmsData.getData()[1] = alarmsItemModel;
        FloatingActionButton alarmFAB = rootView.findViewById(R.id.alarm_insert_fab);
        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddAlarmActivity.class);
                intent.putExtra("alarmsData", gson.toJson(alarmsData));
                startActivity(intent);
            }
        });
        return rootView;
    }


}
