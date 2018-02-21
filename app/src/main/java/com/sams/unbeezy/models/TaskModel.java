package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by Richard on 21-Feb-18.
 */

public class TaskModel implements Serializable {
    private String title;
    private String date;
    private String time;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
