package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.sams.unbeezy.EditTaskActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.controllers.TaskFragmentController;
import com.sams.unbeezy.models.TaskModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class TaskFragment extends Fragment {
    public static final int TASK_REQUEST = 2;
    private final String LOG_TAG = getClass().getSimpleName();
    TaskFragmentController controller;

    Button addTaskButton;
    public RecyclerView taskList;

    Gson gson = new Gson();

    public TaskFragment() {
        controller = new TaskFragmentController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_task, container, false);
        addTaskButton = (Button) rootView.findViewById(R.id.add_task_button);
        taskList = (RecyclerView) rootView.findViewById(R.id.task_list);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });
        return rootView;
    }

    private void addTask(View view) {
        Intent intent = new Intent(getActivity(), EditTaskActivity.class);
        startActivityForResult(intent, TASK_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    String result = data.getStringExtra("editedTask");
                    Log.d(LOG_TAG, String.format("Edited Task: %s", result));
                    Log.d(LOG_TAG, "New task edited");
                    TaskModel taskModel = gson.fromJson(result, TaskModel.class);
                    controller.addData(taskModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Exception found");
                }
            }
        }
    }
}
