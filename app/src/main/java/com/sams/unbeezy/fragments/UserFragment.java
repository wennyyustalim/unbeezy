package com.sams.unbeezy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.lists.DismisserServicesList;
import com.sams.unbeezy.receivers.AlarmReceiver;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class UserFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_user, container, false);
        Button panicButton = rootView.findViewById(R.id.panicAttack);
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), PanicAttackActivity.class);
//                intent.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE, DismisserServicesList.SHAKE_IT_OFF_CODE);
//                startActivity(intent);
            sendBroadcastIntent();;
            }
        });
        return rootView;
    }
    void sendBroadcastIntent() {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ALARM_START_ACTION);
        intent.putExtra("needLocation", true);
        intent.putExtra("description", "GO TO CAMPUS!!!!!");
        getActivity().sendBroadcast(intent);
    }
}
