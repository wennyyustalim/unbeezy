package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by kennethhalim on 2/14/18.
 */

public class SchedulesItemModel implements Serializable {
    private String course_key;
    private String color_hex;
    private String class_room;

    public String getCourseKey() {
        return course_key;
    }
    public String getClassRoom() {
        return class_room;
    }
    public String getColorHex() {
        return color_hex;
    }

    public void setCourseKey(String course_key) {
        this.course_key = course_key;
    }

    public void setColorHex(String color_hex) {
        this.color_hex = color_hex;
    }

    public void setClassRoom(String class_room) {
        this.class_room = class_room;
    }
}
