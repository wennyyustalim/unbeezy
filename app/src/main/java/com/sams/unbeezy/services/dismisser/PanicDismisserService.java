package com.sams.unbeezy.services.dismisser;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public abstract class PanicDismisserService extends IntentService {
    public static String ACTION = "DismissAlarm!";
    public PanicDismisserService(String name) {
        super(name);
    }

    public void dismiss() {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        sendBroadcast(intent);
        stopSelf();
    }
}
