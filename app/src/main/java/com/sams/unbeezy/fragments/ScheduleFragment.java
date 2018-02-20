package com.sams.unbeezy.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sams.unbeezy.AddCourseActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.controllers.CoursesController;
import com.sams.unbeezy.models.CourseModel;
import com.sams.unbeezy.models.CourseScheduleItemModel;
import com.sams.unbeezy.models.SchedulesItemModel;
import com.sams.unbeezy.models.SchedulesModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static android.app.Activity.RESULT_OK;


/**
 * Created by kennethhalim on 2/12/18.
 */

public class ScheduleFragment extends Fragment {
    SchedulesModel schedulesData;
    CourseModel[] coursesArray;
    LayoutInflater inflater;
    LinearLayout coursesListView;
    Gson gson = new Gson();
    int REQUEST_CODE = 1;
    CoursesController coursesController;
    private static String LOG_TAG = "SCHED_FRAGMENT";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CourseModel dumm = new CourseModel();
//        dumm.setCourseId("IF2250");
//        dumm.setCourseName("DASAR REKAYASA");
//        dumm.setLecturerName("Bayu H");
//        dumm.setLecturerEmail("bayu@informatika.org");
//        dumm.setColorHex("#DDAACC");
//        coursesArray.add(dumm);
//        Log.d("SchedF", "onCreate Called");
//        inflater = getActivity().getLayoutInflater();
//        coursesController = new CoursesController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_schedule, container, false);
        schedulesData = new SchedulesModel();
        setTable(rootView);
        FloatingActionButton scheduleFAB = rootView.findViewById(R.id.schedule_insert_fab);
        scheduleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddCourseActivity.class);
                intent.putExtra("scheduleData", gson.toJson(schedulesData));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        coursesListView = rootView.findViewById(R.id.courses_list);
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String intentString = data.getStringExtra("newCourse");
                Log.d(LOG_TAG,intentString);
                CourseModel model = gson.fromJson(intentString,CourseModel.class);

            }
        }
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
//
//    @Override
//    public void onPause() {
//        super.onPause();
////        Log.d("SchedF", "onPause Called");
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        Log.d("SchedF", "onViewCreated Called");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
////        Log.d("SchedF", "onStart Called");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        Log.d("SchedF", "onResume Called");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        Log.d("SchedF", "onDestroy Called");
//    }

    private void adaptLinearLayout(LinearLayout layout, CourseModel[] coursesArray) {
        layout.removeAllViews();
        Log.d("NEWADAPTOR",gson.toJson(coursesArray));
         int height  = 0 ;
        for(CourseModel item : coursesArray) {
            View inflated = inflateLayout(item,layout);
            layout.addView(inflated);
            height += inflated.getMeasuredHeight();
        }
        layout.getLayoutParams().height = height;
    }



    private View inflateLayout(CourseModel model, ViewGroup parent){
        View inflated = inflater.inflate(R.layout.component_courses_list_view, parent, false);
//        TextView courseName =  inflated.findViewById(R.id.course_name);
//        courseName.setText(String.format("%s %s",model.getCourseId(), model.getCourseName()));
//        TextView lecturerName = inflated.findViewById(R.id.lecturer_name);
//        lecturerName.setText(String.format("%s", model.getLecturerName()));
//        TreeMap<String, List<String> > roomMap = new TreeMap<>();
//        for(CourseScheduleItemModel item : model.getSchedules()) {
//            if(roomMap.get(item.getClassRoom()) == null) {
//                List<String> temp = new ArrayList<>();
//                temp.add(item.getTime());
//                roomMap.put(item.getClassRoom(), temp);
//            } else {
//                roomMap.get(item.getClassRoom()).add(item.getTime());
//            }
//        }
//        LinearLayout roomContainer = inflated.findViewById(R.id.room_container);
//        for (Map.Entry<String, List<String>> entry : roomMap.entrySet()) {
//            boolean first = true;
//            String room = "";
//            for(String item : entry.getValue()) {
//                if(first) {
//                    room.concat(item);
//                } else  {
//                    room.concat(",");
//                    room.concat(item);
//                }
//                TextView textView = new TextView(getContext());
//                textView.setText(String.format("%s:%s",room,entry.getKey()));
//                roomContainer.addView(textView);
//            }
//
//        }
//        ImageButton mailIntent = inflated.findViewById(R.id.mail_button);
//        if(model.getLecturerEmail() != null &&!model.getLecturerEmail().equals("")) {
//            final String lecturerMail = model.getLecturerEmail();
//            mailIntent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_EMAIL, lecturerMail);
//                    intent.setData(Uri.parse(String.format("mailto:%s",lecturerMail)));
//                    getContext().startActivity(Intent.createChooser(intent, "Send Email"));
//                }
//            });
//        } else {
//            mailIntent.setVisibility(View.GONE);
//        }


        return inflated;
    }
}
