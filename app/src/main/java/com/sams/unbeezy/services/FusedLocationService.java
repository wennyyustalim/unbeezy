package com.sams.unbeezy.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
public class FusedLocationService extends IntentService {

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
        }
    }


}
