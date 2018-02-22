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
    public static String title;
    public static String _description;
    String description;
    public PanicDismisserService(String name, String title, String description) {
        super(name);
        this.title = title;
        this._description = description;
    }

    public void dismiss() {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        sendBroadcast(intent);
        stopSelf();
    }
    public static String getTitle() {
        return title;
    }
    public static String getDescription() {
        return _description;
    }
}
