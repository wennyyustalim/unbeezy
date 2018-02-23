package com.sams.unbeezy.controllers;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.fragments.ScheduleFragment;
import com.sams.unbeezy.models.CourseModel;
import com.sams.unbeezy.models.SchedulesItemModel;
import com.sams.unbeezy.models.SchedulesModel;
import com.sams.unbeezy.services.DataSyncService;
import com.sams.unbeezy.services.FirebaseDatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class ScheduleFragmentController {
    TreeMap<String,CourseModel> coursesData;
    DatabaseReference coursesDataRef;
    ScheduleFragment fragment;
    DatabaseReference scheduleDataRef;
    SchedulesModel schedulesData;

    Gson gson;
    static String LOG_TAG = "UNBEEZY_COURSES_CONTROLLER";
    public ScheduleFragmentController(ScheduleFragment fragment) {
        this.fragment = fragment;
        coursesDataRef = FirebaseDatabaseService.getInstance().child("courses");
        scheduleDataRef = FirebaseDatabaseService.getInstance().child("schedules");
        gson = new Gson();
        updateData();
        updateScheduleData();
    }
    public void updateData() {
        coursesDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coursesData = new TreeMap<>();
                if(dataSnapshot.getValue() != null) {
                   for(DataSnapshot item : dataSnapshot.getChildren()) {
                       coursesData.put(item.getKey(),item.getValue(CourseModel.class));
                   }
                   fragment.updateLayout(coursesData);
                    Intent intent = new Intent(fragment.getContext(), DataSyncService.class);
                    fragment.getActivity().startService(intent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                coursesData = new TreeMap<>();
                fragment.updateLayout(coursesData);

            }
        });
    }
    public void addData(CourseModel model) {
        coursesDataRef.push().setValue(model);
        updateData();

    }

    public void flushData() {
        coursesData = new TreeMap<>();
        fragment.updateLayout(coursesData);
        schedulesData = new SchedulesModel();
        fragment.updateScheduleTable(schedulesData);

    }

    public void updateScheduleData() {
        scheduleDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedulesData = new SchedulesModel();
                for(DataSnapshot i : dataSnapshot.getChildren()) {
                    for(DataSnapshot j : i.getChildren()) {
                        schedulesData.getData()[Integer.parseInt(i.getKey())][Integer.parseInt(j.getKey())] = j.getValue(SchedulesItemModel.class);
                    }

                }
                fragment.updateScheduleTable(schedulesData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void deleteCourseData(String key) {
        coursesDataRef.child(key).removeValue();
        updateData();
        flushData();
    }
}
