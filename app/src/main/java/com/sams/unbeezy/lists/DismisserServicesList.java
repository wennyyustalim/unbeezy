package com.sams.unbeezy.lists;

import com.sams.unbeezy.services.dismisser.ArduitnowDismisserService;
import com.sams.unbeezy.services.dismisser.PanicDismisserService;
import com.sams.unbeezy.services.dismisser.RiseAndShineDismisserService;
import com.sams.unbeezy.services.dismisser.ShakeItOffDismisserService;

import java.lang.reflect.Method;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class DismisserServicesList {
    public static final String RISE_AND_SHINE_CODE = "RASDSERVICE";
    public static final String SHAKE_IT_OFF_CODE = "SIODSERVICE";
    public static final String AR_DU_IT_NOW_CODE = "ADINDSERVICE";
    public static Class RISE_AND_SHINE = RiseAndShineDismisserService.class;
    public static Class SHAKE_IT_OFF = ShakeItOffDismisserService.class;
    public static Class AR_DU_IT_NOW = ArduitnowDismisserService.class;
    public static String DISMISSER_CLASS_INTENT_CODE = "DissmisserService";

    public static Class getDismisserClass(String code) {
        try {
            switch (code) {
                case RISE_AND_SHINE_CODE:
                    RISE_AND_SHINE.newInstance();
                    return RISE_AND_SHINE;
                case SHAKE_IT_OFF_CODE:
                    SHAKE_IT_OFF.newInstance();
                    return SHAKE_IT_OFF;
                case AR_DU_IT_NOW_CODE:
                    AR_DU_IT_NOW.newInstance();
                    return AR_DU_IT_NOW;
                default:
                    SHAKE_IT_OFF.newInstance();
                    return SHAKE_IT_OFF;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RISE_AND_SHINE;
    }

    public static String getTitle(String code) {
        String type = "";
        try {
            Method m = getDismisserClass(code).getMethod("getTitle");
            type = (String) m.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }

    public static String getDescription(String code) {
        String type = "";
        try {
            Method m = getDismisserClass(code).getMethod("getDescription");
            type = (String) m.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }
}
