package com.sams.unbeezy.controllers;

import android.util.ArrayMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.fragments.AlarmFragment;
import com.sams.unbeezy.models.AlarmModel;
import com.sams.unbeezy.services.FirebaseDatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by wennyyustalim on 21/02/18.
 */

public class AlarmFragmentController {
    static String LOG_TAG = "UNBEEZY_ALARM_CONTROLLER";

    ArrayMap<String, AlarmModel> dataStore;
    DatabaseReference databaseReference;

    AlarmFragment fragment;
    Gson gson;

    public AlarmFragmentController(AlarmFragment fragment) {
        this.fragment = fragment;
        databaseReference = FirebaseDatabaseService.getInstance().child("alarms");
        gson = new Gson();
        updateData();
    }

    public void addData(AlarmModel model) {
        databaseReference.push().setValue(model);
        updateData();
    }

    public void updateData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataStore = new ArrayMap<>();
                if(dataSnapshot.getValue() != null) {
                    for(DataSnapshot item : dataSnapshot.getChildren()) {
                        dataStore.put(item.getKey(),item.getValue(AlarmModel.class));
                    }
                    fragment.updateLayout(dataStore);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void flushLayout() {
        dataStore = new ArrayMap<>();
        fragment.updateLayout(dataStore);
    }
    public void deleteData(String key) {
        databaseReference.child(key).removeValue();
        flushLayout();
    }
}
