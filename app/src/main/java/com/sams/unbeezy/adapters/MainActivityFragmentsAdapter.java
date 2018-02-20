package com.sams.unbeezy.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.sams.unbeezy.R;
import com.sams.unbeezy.fragments.AlarmFragment;
import com.sams.unbeezy.fragments.ScheduleFragment;
import com.sams.unbeezy.fragments.TaskFragment;
import com.sams.unbeezy.fragments.UserFragment;

/**
 * Created by kennethhalim on 2/12/18.
 */

public class MainActivityFragmentsAdapter extends FragmentStatePagerAdapter {
    int FRAGMENT_COUNT = 4;

    public MainActivityFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        Bundle args = new Bundle();
        switch (i) {
            case 0: fragment = new ScheduleFragment(); break;
            case 1: fragment = new TaskFragment(); break;
            case 2: fragment = new AlarmFragment(); break;
            case 3: fragment = new UserFragment(); break;
            default: fragment = new UserFragment(); break;
        }
        // Our object is just an integer :-P
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String titles[] = {
                "SCHED",
                "TASK",
                "ALARM",
                "USER"
        };
        return titles[position];
    }
}
