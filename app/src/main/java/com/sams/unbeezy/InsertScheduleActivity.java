package com.sams.unbeezy;

import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class InsertScheduleActivity extends BaseActivity {
    String ACTIVITY_TITLE ="Add New Schedule";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_schedule);
        setToolbar(ACTIVITY_TITLE);
    }
}
