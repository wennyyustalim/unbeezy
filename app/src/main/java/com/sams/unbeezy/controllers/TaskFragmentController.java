package com.sams.unbeezy.controllers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.ArrayMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.adapters.TaskItemAdapter;
import com.sams.unbeezy.fragments.TaskFragment;
import com.sams.unbeezy.models.TaskModel;
import com.sams.unbeezy.services.FirebaseDatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 21-Feb-18.
 */

public class TaskFragmentController {
    static String LOG_TAG = "UNBEEZY_TASK_CONTROLLER";

    ArrayMap<String, TaskModel> dataStore;
    DatabaseReference databaseReference;

    TaskFragment fragment;
//    Context context;
    Gson gson;

    public TaskFragmentController(TaskFragment fragment) {
        this.fragment = fragment;
//        this.context = fragment.getActivity().getApplicationContext();
        databaseReference = FirebaseDatabaseService.getInstance().child("tasks");
        gson = new Gson();
        updateData();
    }

    public void addData(TaskModel model) {
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
                        dataStore.put(item.getKey(), item.getValue(TaskModel.class));
                    }
                    TaskItemAdapter taskItemAdapter = new TaskItemAdapter(fragment.getActivity().getApplicationContext(), dataStore, TaskFragmentController.this);
                    fragment.taskList.setAdapter(taskItemAdapter);
                    fragment.taskList.setLayoutManager(new LinearLayoutManager(fragment.getActivity().getApplicationContext()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void flushLayout() {
        dataStore = new ArrayMap<>();
        TaskItemAdapter taskItemAdapter = new TaskItemAdapter(fragment.getActivity().getApplicationContext(), dataStore, this);
        fragment.taskList.setAdapter(taskItemAdapter);
        fragment.taskList.setLayoutManager(new LinearLayoutManager(fragment.getActivity().getApplicationContext()));
    }
    public void deleteData(String key ) {
        databaseReference.child(key).removeValue();
        flushLayout();
    }
}
