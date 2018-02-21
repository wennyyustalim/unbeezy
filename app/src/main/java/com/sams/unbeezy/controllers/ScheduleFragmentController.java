package com.sams.unbeezy.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
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
import com.sams.unbeezy.services.FirebaseDatabaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class ScheduleFragmentController {
    List<CourseModel> dataStore;
    DatabaseReference databaseReference;
    ScheduleFragment fragment;
    DatabaseReference scheduleDataRef;
    SchedulesModel schedulesData;

    Gson gson;
    static String LOG_TAG = "UNBEEZY_COURSES_CONTROLLER";
    public ScheduleFragmentController(ScheduleFragment fragment) {
        this.fragment = fragment;
        databaseReference = FirebaseDatabaseService.getInstance().child("courses");
        scheduleDataRef = FirebaseDatabaseService.getInstance().child("schedules");
        gson = new Gson();
        updateData();
    }
    public void updateData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataStore = new ArrayList<>();
                if(dataSnapshot.getValue() != null) {
                   for(DataSnapshot item : dataSnapshot.getChildren()) {
                       dataStore.add(item.getValue(CourseModel.class));
                   }
                   fragment.updateLayout(dataStore);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addData(CourseModel model) {
        databaseReference.push().setValue(model);
        updateData();
    }

    public void updateScheduleData() {
        scheduleDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedulesData = new SchedulesModel();
                schedulesData.setData(dataSnapshot.getValue(SchedulesItemModel[][].class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
