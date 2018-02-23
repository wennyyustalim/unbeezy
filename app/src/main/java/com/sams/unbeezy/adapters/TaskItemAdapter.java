package com.sams.unbeezy.adapters;

import android.app.FragmentController;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sams.unbeezy.R;
import com.sams.unbeezy.controllers.TaskFragmentController;
import com.sams.unbeezy.fragments.TaskFragment;
import com.sams.unbeezy.models.TaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Richard on 21-Feb-18.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder> {
    private LayoutInflater mInflater;
    private ArrayMap<String, TaskModel> models;
    private Calendar today = Calendar.getInstance();
    private Calendar taskDate = Calendar.getInstance();
    private TaskFragmentController controller;
    public TaskItemAdapter(Context context, ArrayMap<String, TaskModel> models, TaskFragmentController controller) {
        mInflater = LayoutInflater.from(context);
        this.models = models;
        this.controller = controller;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.component_task_list_item, parent, false);
        return new TaskViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(TaskItemAdapter.TaskViewHolder holder, int position) {
        final TaskModel model = models.valueAt(position);
        final int constpost = position;
        holder.parentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                controller.deleteData(models.keyAt(constpost));
                return true;
            }
        });
        holder.taskTitle.setText(model.getTitle());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        try {
            taskDate.setTime(sdf.parse(model.getDate() + " " + model.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long daysRemainingInMillis = taskDate.getTimeInMillis() - today.getTimeInMillis();
        long daysRemaining = daysRemainingInMillis / (24 * 60 * 60 * 1000);
        holder.numOfDaysRemaining.setText(String.format("%d", daysRemaining));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public View parentView;
        final TextView taskTitle;
        final TextView date;
        final TextView time;
        final TextView numOfDaysRemaining;
        final TaskItemAdapter mAdapter;

        TaskViewHolder(View itemView, TaskItemAdapter adapter) {
            super(itemView);
            parentView = itemView;
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            date = (TextView) itemView.findViewById(R.id.task_date);
            time = (TextView) itemView.findViewById(R.id.task_time);
            numOfDaysRemaining = (TextView) itemView.findViewById(R.id.num_of_days_remaining);
            this.mAdapter = adapter;
        }
    }
}
