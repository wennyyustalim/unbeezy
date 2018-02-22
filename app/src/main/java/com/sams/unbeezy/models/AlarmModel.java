package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AlarmModel implements Serializable {
    private int hour;
    private int minute;
    private Boolean on  = Boolean.FALSE;

    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public Boolean isOn() { return on; }

    public void setHour(int hour) { this.hour = hour; }
    public void setMinute(int minute) { this.minute = minute; }
    public void switchOn() { this.on = Boolean.TRUE; }
    public void switchOff() { this.on = Boolean.FALSE; }
}
