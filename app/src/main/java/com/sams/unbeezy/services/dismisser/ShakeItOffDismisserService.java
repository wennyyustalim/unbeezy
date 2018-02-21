package com.sams.unbeezy.services.dismisser;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ShakeItOffDismisserService extends PanicDismisserService implements SensorEventListener {
    public ShakeItOffDismisserService() {
        super("ShakeItOffDisimisserService");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }


}
