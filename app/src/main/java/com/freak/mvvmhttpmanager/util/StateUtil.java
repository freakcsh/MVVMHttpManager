package com.freak.mvvmhttpmanager.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;

/**
 * @author Freak
 * @date 2019/5/16.
 */

public class StateUtil {
    public static void transStatusColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
