package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sams.unbeezy.EditTaskActivity;
import com.sams.unbeezy.R;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class TaskFragment extends Fragment {
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

    public void addTask(View view) {
        Intent intent = new Intent(getActivity(), EditTaskActivity.class);
        startActivity(intent);
    }
}
