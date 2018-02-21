package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sams.unbeezy.EditTaskActivity;
import com.sams.unbeezy.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class TaskFragment extends Fragment {
    public static final int TASK_REQUEST = 2;
    private final String LOG_TAG = getClass().getSimpleName();
    Button addTaskButton;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_task, container, false);
        addTaskButton = (Button) rootView.findViewById(R.id.add_task_button);
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
                String result = data.getStringExtra("editedTask");
                if (result != null) {
                    Log.d(LOG_TAG, String.format("Edited Task: %s", result));
                    Log.d(LOG_TAG, "New task edited");
                } else {
                    Log.d(LOG_TAG, "Result is null");
                }
            }
        }
    }
}
