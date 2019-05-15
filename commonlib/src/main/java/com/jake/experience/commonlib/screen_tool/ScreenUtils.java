package com.jake.experience.commonlib.screen_tool;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Point;
import android.os.PowerManager;
import android.view.Display;
import android.view.WindowManager;


import static android.content.Context.KEYGUARD_SERVICE;



public class ScreenUtils {

    private static float screenScale = -1f;

    // 获得屏幕宽
    public static int getSWidth(Context context) {
        Point point = getScreenPoint(context);
        return Math.min(point.x, point.y);
    }

    // 获得屏幕高
    public static int getSHeight(Context context ) {
        Point point = getScreenPoint(context);
        return Math.max(point.x, point.y);
    }

    // 获得屏幕高宽
    public static Point getScreenPoint(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        @SuppressWarnings("deprecation") Point point = new Point(display.getWidth(), display.getHeight());
        return point;
    }

    public static int dp2px(Context context , float dpValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context ,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int toPx(Context context , int value) {
        if (screenScale <= 0) {
            screenScale = getScreenScale(context);
        }
        return Math.round(value * screenScale);
    }

    public static float getScreenScale(Context context) {
        Point point = getScreenPoint(context);
        return Math.max(point.x, point.y) / 1280.0f;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 点亮屏幕
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static void screenWakeup(Context context) {
// 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁

//        Intent intent_main = new Intent(AiApplication.getContext(), MainActivity.class);
//        intent_main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent_main.setAction("from_wakeup");
//        if(!AiApp.isMainActivityShowing()){
//            AiApplication.getContext().startActivity(intent_main);
//        }


    }
}