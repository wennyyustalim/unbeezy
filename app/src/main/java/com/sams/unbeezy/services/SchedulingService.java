package com.sams.unbeezy.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.models.AlarmModel;

import java.util.ArrayList;
import java.util.List;

public class SchedulingService extends IntentService {
    private String LOG_TAG = "SchedulingService";

    List<AlarmModel> dataStore;
    DatabaseReference databaseReference;

    Gson gson;

    public SchedulingService() {
        super("SchedulingService");
        databaseReference = FirebaseDatabaseService.getInstance().child("alarms");
        gson = new Gson();
        getData();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                for (AlarmModel item : dataStore) {
                    Log.d(LOG_TAG, String.format("Hour: %d, Minute: %d", item.getHour(), item.getMinute()));
                }
                Log.d(LOG_TAG, "Service is running");
            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "No alarms in datastore");
            }
        }
    }

    public void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataStore = new ArrayList<>();
                if (dataSnapshot.getValue() != null) {
                    Log.d(LOG_TAG, "DataSnapshot is not null");

                    for(DataSnapshot item : dataSnapshot.getChildren()) {
                        dataStore.add(item.getValue(AlarmModel.class));
                    }
                    for (AlarmModel item : dataStore) {
                        Log.d(LOG_TAG, String.format("Hour: %d, Minute: %d", item.getHour(), item.getMinute()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
