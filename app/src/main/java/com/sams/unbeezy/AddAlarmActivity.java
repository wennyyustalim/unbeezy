package com.sams.unbeezy;

import android.os.Bundle;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AddAlarmActivity extends BaseActivity {
    String ACTIVITY_TITLE = "Add New Alarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        setToolbar(ACTIVITY_TITLE);
    }
}
