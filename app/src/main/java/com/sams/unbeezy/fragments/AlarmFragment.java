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

import com.sams.unbeezy.AddAlarmActivity;
import com.sams.unbeezy.AddCourseActivity;
import com.sams.unbeezy.R;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class AlarmFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_alarm, container, false);
        FloatingActionButton alarmFAB = rootView.findViewById(R.id.alarm_insert_fab);
        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddAlarmActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
