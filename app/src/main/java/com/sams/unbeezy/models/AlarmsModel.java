package com.sams.unbeezy.models;

import java.io.Serializable;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AlarmsModel implements Serializable {
    private AlarmsItemModel[] data;

    public AlarmsModel() {
        data = new AlarmsItemModel[10];
    }

    public AlarmsItemModel[] getData() {
        return data;
    }
}
