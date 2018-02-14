package com.sams.unbeezy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class AddCourseActivity extends BaseActivity {
    String ACTIVITY_TITLE ="Add New Schedule";
    ColorPicker cp;
    int selectedColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setToolbar(ACTIVITY_TITLE);
        ImageButton colorFlagButton = findViewById(R.id.button_select_color);
        colorFlagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showColorPicker();
            }
        });
        cp = new ColorPicker(AddCourseActivity.this, 0, 0, 0);;
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
