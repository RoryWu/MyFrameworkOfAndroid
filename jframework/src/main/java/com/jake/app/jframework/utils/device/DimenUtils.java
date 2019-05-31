package com.jake.app.jframework.utils.device;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.jake.app.jframework.app.JCore;

/**
 * @author
 * @version 1.0
 * @date 19-5-31
 */
public class DimenUtils {

    public static int getScreenWidth() {
        final Resources resources = JCore.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = JCore.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
