package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by kennethhalim on 2/14/18.
 */

public class SchedulesItemModel implements Serializable {
    private String course_key;
    private String color_hex;

    public String getCourseKey() {
        return course_key;
    }

    public String getColorHex() {
        return color_hex;
    }
}
