package com.sams.unbeezy.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by kennethhalim on 2/22/18.
 */

public class UnbeezyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        FirebaseDatabaseService.getInstance().child("fcm_token").setValue(FirebaseInstanceId.getInstance().getToken());

    }
}
