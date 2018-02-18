package com.sams.unbeezy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.sams.unbeezy.models.SchedulesModel;

public class AddCourseActivity extends BaseActivity {
    String ACTIVITY_TITLE ="Add New Schedule";
    ColorPicker cp;
    int selectedColor;
    String scheduleString;
    int REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setToolbar(ACTIVITY_TITLE);
        scheduleString = getIntent().getStringExtra("scheduleData");
        Log.d("SAMS", scheduleString);
        ImageButton colorFlagButton = findViewById(R.id.button_select_color);
        colorFlagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showColorPicker();
            }
        });
        cp = new ColorPicker(AddCourseActivity.this, 0, 0, 0);;


    }

    @Override
    public void onStart() {
        super.onStart();
        ImageButton scheduleButton = findViewById(R.id.button_select_schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectScheduleActivity();
            }
        });
    }

    private void showSelectScheduleActivity() {
        Intent intent = new Intent(this, SelectScheduleActivity.class);
        intent.putExtra("scheduleData", scheduleString);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                Log.d("UNBEEZY",(data.getStringExtra("scheduleList")));
            }
        }
    }
    private void showColorPicker() {
        cp.show();
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                selectedColor = color;
                cp.dismiss();
                ConstraintLayout colorSample = findViewById(R.id.color_sample);
                colorSample.setBackgroundColor(color);
            }
        });
    }
}
