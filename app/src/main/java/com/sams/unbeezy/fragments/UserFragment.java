package com.sams.unbeezy.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.lists.DismisserServicesList;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class UserFragment extends Fragment {
    private String LOG_TAG = "UserFragment";
    TextView personName;
    TextView userNameAcronym;
    String personNameStr;
    SharedPreferences pref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        pref = this.getActivity().getSharedPreferences("UnbeezyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // Set default alarm dismisser to shake it off mode
        editor.putInt("dismisserMode", 1);
        editor.apply();
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
                Intent intent = new Intent(getContext(), PanicAttackActivity.class);
                intent.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE, DismisserServicesList.SHAKE_IT_OFF_CODE);
                startActivity(intent);
            }
        });

        final ToggleButton dismisserToggleButton = rootView.findViewById(R.id.dismisser_toggle);
        dismisserToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage;
                SharedPreferences.Editor editor = pref.edit();
                if(dismisserToggleButton.isChecked()) {
                    toastMessage = "Rise and Shine Mode";
                    editor.putInt("dismisserMode", 0);
                } else {
                    toastMessage = "Shake It Off Mode";
                    editor.putInt("dismisserMode", 1);
                }
                editor.apply();
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
        personName = (TextView) rootView.findViewById(R.id.person_name);
        userNameAcronym = (TextView) rootView.findViewById(R.id.user_name_acronym);
        try {
            personNameStr = pref.getString("personName", null);
            String[] names = personNameStr.split(" ");
            String acronym = null;
            try {
                acronym = names[0].substring(0,1) + names[1].substring(0,1);
            } catch (NullPointerException e) {
                acronym = names[0].substring(0,1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            personName.setText(personNameStr);
            userNameAcronym.setText(acronym);
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, "Person's Name not found");

        }

        return rootView;
    }
}
