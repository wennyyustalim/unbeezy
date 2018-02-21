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
    public DataSyncService() {
        super("DataSyncService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                String result;
                boolean first = true;
                do {
                    result = GenericAPICaller.sendRequest
                            ("schedule/update",
                                    "POST",
                                    "application/x-www-form-urlencoded",
                                    String.format("uid=%s",URLEncoder.encode(FirebaseAuth.getInstance().getUid(), "UTF-8")));
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
