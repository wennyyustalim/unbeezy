package com.sams.unbeezy.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.sams.unbeezy.helpers.GenericAPICaller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DataSyncService extends IntentService {

    public static final String SCHEDULE_UPDATE_ACTION = "SCHEDULE_UPDATE";
    public static final String SCHEDULE_UPDATE_URL = "schedule/update";
    public static final String PUSH_NOTIF_ACTION = "PUSH_NOTIF";
    public static final String PUSH_NOTIF_URL = "sendNotif";

    public String getURL(String code) {
        switch (code) {
            case SCHEDULE_UPDATE_ACTION:
                return SCHEDULE_UPDATE_URL;
            case PUSH_NOTIF_ACTION:
                return PUSH_NOTIF_URL;
            default:
                return SCHEDULE_UPDATE_URL;
        }
    }
    public DataSyncService() {
        super("DataSyncService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String intentAction = intent.getAction()+"";
            String url = getURL(intentAction);
                try {
                    String result;
                    boolean first = true;
                    do {
                        result = GenericAPICaller.sendRequest
                                (url,
                                        "POST",
                                        "application/x-www-form-urlencoded",
                                        String.format("uid=%s", URLEncoder.encode(FirebaseAuth.getInstance().getUid(), "UTF-8")));
                        if (!first) {
                            Thread.sleep(500);
                        } else {
                            first = false;
                        }
                    } while (!result.equals("OK"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
}
