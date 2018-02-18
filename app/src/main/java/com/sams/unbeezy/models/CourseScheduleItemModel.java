package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by kennethhalim on 2/19/18.
 */

public class CourseScheduleItemModel implements Serializable {
    private String time;
    private String class_room;

    public String getTime() {
        return time;
    }

    public String getClassRoom() {
        return class_room;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setClassRoom(String class_room) {
        this.class_room = class_room;
    }
}