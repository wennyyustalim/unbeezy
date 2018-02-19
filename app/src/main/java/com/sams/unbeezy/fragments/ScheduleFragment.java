package com.sams.unbeezy.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sams.unbeezy.AddCourseActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.models.SchedulesItemModel;
import com.sams.unbeezy.models.SchedulesModel;

import java.util.Random;


/**
 * Created by kennethhalim on 2/12/18.
 */

public class ScheduleFragment extends Fragment {

    SchedulesModel schedulesData;
    Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_schedule, container, false);
        schedulesData = new SchedulesModel();
        SchedulesItemModel schedulesItemModel = new SchedulesItemModel();
        schedulesItemModel.setCourseKey("IF2250");
        schedulesItemModel.setColorHex("#FF00FF");
        schedulesItemModel.setClassRoom("7606");
        schedulesData.getData()[1][2] = schedulesItemModel;
        FloatingActionButton scheduleFAB = rootView.findViewById(R.id.schedule_insert_fab);
        scheduleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddCourseActivity.class);
                intent.putExtra("scheduleData", gson.toJson(schedulesData));
                startActivity(intent);
            }
        });
        setTable(rootView);
        return rootView;
    }

    private void setTable(View view) {

        Context context = getContext();
        TableLayout tableLayout = view.findViewById(R.id.table_schedule);
        for (int i =0; i<11;i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setWeightSum(6);
            TextView textview = new TextView(context);
            textview.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textview.setBackground(getResources().getDrawable(R.drawable.border_right_primary));
            textview.setText(String.format("%02d.00", i+7));
            textview.setHeight(60);
            textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tableRow.addView(textview);

            for(int j=1;j<=7;j++) {
                TextView textViewColumn = new TextView(context);
                textViewColumn.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                textViewColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                Random random = new Random();
                textViewColumn.setBackgroundColor(random.nextInt()%256000);
                textViewColumn.setText(String.format("%d%d", j,i+1));
                textViewColumn.setHeight(80);
                textViewColumn.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tableRow.addView(textViewColumn);
                textview.setPadding(1,1,1,1);
            }
            tableLayout.addView(tableRow);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
