package com.sams.unbeezy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditTaskActivity extends BaseActivity {
    String ACTIVITY_TITLE = "Edit Task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setToolbar(ACTIVITY_TITLE);
    }
}
