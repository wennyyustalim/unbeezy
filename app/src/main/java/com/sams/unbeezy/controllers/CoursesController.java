package com.sams.unbeezy.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sams.unbeezy.models.CourseModel;
import com.sams.unbeezy.services.FirebaseDatabaseService;

import java.util.List;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class CoursesController {
    CourseModel[] dataStore;
    DatabaseReference databaseReference;
    Fragment fragment;
    Gson gson;
    static String LOG_TAG = "UNBEEZY_COURSES_CONTROLLER";
    public CoursesController(Fragment fragment) {
        this.fragment = fragment;
        databaseReference = FirebaseDatabaseService.getInstance().child("courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                gson.fromJson(gson.toJson(dataSnapshot.getValue()),CourseModel[].class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addData() {

    }


}
