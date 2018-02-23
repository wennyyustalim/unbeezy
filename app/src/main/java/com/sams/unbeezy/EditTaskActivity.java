package com.sams.unbeezy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sams.unbeezy.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTaskActivity extends BaseActivity {
    private final String LOG_TAG = EditTaskActivity.class.getSimpleName();
    private Calendar calendar = Calendar.getInstance();
    private TextView date_picker;
    private TextView time_picker;
    private Button save_button;
    TaskModel model;

    String ACTIVITY_TITLE = "Edit Task";
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, date);
            updateDateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            updateTimeLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setToolbar(ACTIVITY_TITLE);
        model = new TaskModel();
        date_picker = (TextView) findViewById(R.id.date_picker);
        time_picker = (TextView) findViewById(R.id.time_picker);
        save_button = (Button) findViewById(R.id.save_button);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTaskActivity.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditTaskActivity.this,
                        time,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void updateDateLabel() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        date_picker.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void updateTimeLabel() {
        String format = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        time_picker.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private boolean isFormFilled() {
        EditText task_title = (EditText) findViewById(R.id.task_title);
        TextView date_picker = (TextView) findViewById(R.id.date_picker);
        TextView time_picker = (TextView) findViewById(R.id.time_picker);

        String taskTitle = task_title.getText().toString();
        String date = date_picker.getText().toString();
        String time = time_picker.getText().toString();

        model.setTitle(taskTitle);
        model.setDate(date);
        model.setTime(time);

        return (!taskTitle.isEmpty() && !date.isEmpty() && !time.isEmpty());
    }

    private boolean isTaskDateValid() {
        TextView date_picker = (TextView) findViewById(R.id.date_picker);
        TextView time_picker = (TextView) findViewById(R.id.time_picker);

        String date = date_picker.getText().toString();
        String time = time_picker.getText().toString();
        Log.d(LOG_TAG, date);
        Log.d(LOG_TAG, time);

        String[] dateComponents = date.split("/");
        String[] timeComponents = time.split(":");
        Log.d(LOG_TAG, dateComponents[0] + "/" + dateComponents[1] + "/" + dateComponents[2]);
        Log.d(LOG_TAG, timeComponents[0] + ":" + timeComponents[1]);

        Calendar now = Calendar.getInstance();
        Calendar taskDate = Calendar.getInstance();
        taskDate.set(Calendar.YEAR, Integer.parseInt(dateComponents[2]));
        taskDate.set(Calendar.MONTH, (Integer.parseInt(dateComponents[1]) - 1));
        taskDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateComponents[0]));
        taskDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeComponents[0]));
        taskDate.set(Calendar.MINUTE, Integer.parseInt(timeComponents[1]));

        Log.d(LOG_TAG, "task date: " + taskDate.getTimeInMillis());
        Log.d(LOG_TAG, "now: " + now.getTimeInMillis());

        return (taskDate.compareTo(now) > 0);
    }

    private void saveTask() {
        if (isFormFilled()) {
            if (isTaskDateValid()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedTask", gson.toJson(model));
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Please enter a correct task time", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill all fields in the form", Toast.LENGTH_SHORT).show();
        }
    }
}
