package com.sams.unbeezy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kennethhalim on 2/14/18.
 */

public class SchedulesModel implements Serializable{
    private SchedulesItemModel[][] data;

    public SchedulesModel() {
        data = new SchedulesItemModel[13][8];
    }

    public SchedulesItemModel[][] getData() {
        return data;
    }

    public void setData(SchedulesItemModel[][] data) {
        this.data = data;
    }

}
