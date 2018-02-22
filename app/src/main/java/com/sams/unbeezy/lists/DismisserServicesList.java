package com.sams.unbeezy.enums;

import com.sams.unbeezy.services.dismisser.RiseAndShineDismisserService;
import com.sams.unbeezy.services.dismisser.ShakeItOffDismisserService;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class DismisserServicesList {
    public static int RISE_AND_SHINE_CODE = 1;
    public static int SHAKE_IT_OFF_CODE = 2;
    public static Class RISE_AND_SHINE = RiseAndShineDismisserService.class;
    public static Class SHAKE_IT_OFF = ShakeItOffDismisserService.class;

    public static String DISMISSER_CLASS_INTENT_CODE = "DissmisserService";

    public Class getDismisserClass(int code) {
        switch (code) {
            case RISE_AND_SHINE_CODE:
                return RISE_AND_SHINE;
            case SHAKE_IT_OFF_CODE:
                return SHAKE_IT_OFF;
            default:
                return SHAKE_IT_OFF;
        }
    }
}
