package com.sams.unbeezy;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sams.unbeezy.adapters.MainActivityFragmentsAdapter;

public class MainActivity extends BaseActivity {

    MainActivityFragmentsAdapter mainActivityFragmentsAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
//        Button button = findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });
        mainActivityFragmentsAdapter = new MainActivityFragmentsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mainActivityFragmentsAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        setTabIcons(tabLayout);
//        tabLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
//            }
//        });
//
//        mainActivityFragmentsAdapter
    }
    int position;
    @Override
    protected void onPause() {
        super.onPause();
        position = viewPager.getCurrentItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(position);
    }

    public void setTabIcons(TabLayout tabLayout) {
        int icons[]= {
                R.drawable.ic_calendar,
                R.drawable.ic_task_complete,
                R.drawable.ic_alarm_clock,
                R.drawable.ic_man_user
        };
        for(int i = 0; i < icons.length; i++) {
          tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
}
