package com.sams.unbeezy.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sams.unbeezy.R;
import com.sams.unbeezy.lists.DismisserServicesList;
import com.sams.unbeezy.receivers.AlarmReceiver;

import org.w3c.dom.Text;

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
        editor.putString("dismisserMode", DismisserServicesList.SHAKE_IT_OFF_CODE);
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
//                Intent intent = new Intent(getContext(), PanicAttackActivity.class);
//                intent.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE, DismisserServicesList.SHAKE_IT_OFF_CODE);
//                startActivity(intent);
                sendBroadcastIntent();;
            }
        });

        final Spinner dismisserSpinner = rootView.findViewById(R.id.dismisserSpinner);

        ArrayAdapter<CharSequence> panicAttackDismisserTypeArrayAdapter =
                ArrayAdapter.createFromResource(this.getActivity(),
                        R.array.dismisser_types,
                        android.R.layout.simple_dropdown_item_1line);

        dismisserSpinner.setAdapter(panicAttackDismisserTypeArrayAdapter);

        dismisserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String toastMessage;
                SharedPreferences.Editor editor = pref.edit();
                String selectedType = dismisserSpinner.getSelectedItem().toString();
                switch (selectedType) {
                    case "SHAKE IT OFF":
                        toastMessage = "Shake It Off Mode";
                        editor.putString("dismisserMode", DismisserServicesList.SHAKE_IT_OFF_CODE);
                        break;
                    case "RISE AND SHINE":
                        toastMessage = "Rise and Shine Mode";
                        editor.putString("dismisserMode", DismisserServicesList.RISE_AND_SHINE_CODE);
                        break;
                    case "AR-DU-IT-NOW!":
                        toastMessage = "Ar-du-it-now! Mode";
                        editor.putString("dismisserMode", DismisserServicesList.AR_DU_IT_NOW_CODE);
                        break;
                    default:
                        toastMessage = "Shake It Off Mode";
                        editor.putString("dismisserMode", DismisserServicesList.SHAKE_IT_OFF_CODE);
                        break;
                }
                editor.apply();
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        final ToggleButton dismisserToggleButton = rootView.findViewById(R.id.dismisser_toggle);
//        dismisserToggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String toastMessage;
//                SharedPreferences.Editor editor = pref.edit();
//                if(dismisserToggleButton.isChecked()) {
//                    toastMessage = "Rise and Shine Mode";
//                    editor.putString("dismisserMode", DismisserServicesList.RISE_AND_SHINE_CODE);
//                } else {
//                    toastMessage = "Shake It Off Mode";
//                    editor.putString("dismisserMode", DismisserServicesList.SHAKE_IT_OFF_CODE);
//                }
//                editor.apply();
//                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
//            }
//        });
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
    void sendBroadcastIntent() {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ALARM_CHECK_LOCATION);
        intent.putExtra("needLocation", true);
        intent.putExtra("description", "GO TO CAMPUS!!!!!");
        getActivity().sendBroadcast(intent);
    }
}
