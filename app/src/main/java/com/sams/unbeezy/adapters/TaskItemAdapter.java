package com.sams.unbeezy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sams.unbeezy.R;
import com.sams.unbeezy.models.TaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Richard on 21-Feb-18.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<TaskModel> models;
    private Calendar today = Calendar.getInstance();
    private Calendar taskDate = Calendar.getInstance();

    public TaskItemAdapter(Context context, ArrayList<TaskModel> models) {
        mInflater = LayoutInflater.from(context);
        this.models = models;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.component_task_list_item, parent, false);
        return new TaskViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(TaskItemAdapter.TaskViewHolder holder, int position) {
        TaskModel model = models.get(position);
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
        final TextView taskTitle;
        final TextView date;
        final TextView time;
        final TextView numOfDaysRemaining;
        final TaskItemAdapter mAdapter;

        TaskViewHolder(View itemView, TaskItemAdapter adapter) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            date = (TextView) itemView.findViewById(R.id.task_date);
            time = (TextView) itemView.findViewById(R.id.task_time);
            numOfDaysRemaining = (TextView) itemView.findViewById(R.id.num_of_days_remaining);
            this.mAdapter = adapter;
        }
    }
}
