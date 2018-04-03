package com.sams.unbeezy.services.dismisser;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Richard on 03-Apr-18.
 */

public class ArduitnowDismisserService extends PanicDismisserService {
    private final String LOG_TAG = this.getClass().getSimpleName();

    public ArduitnowDismisserService() {
        super("ArduitnowDismisserService", "Ar-du-it-now!", "Press the push button 20 times!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Service Created");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

        }
    }
}
