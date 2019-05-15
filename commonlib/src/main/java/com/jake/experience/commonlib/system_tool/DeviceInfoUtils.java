package com.jake.experience.commonlib.system_tool;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;


public class DeviceInfoUtils {
    private String[] array;

    private DeviceInfoUtils() {
    }

    /**
     * 获取手机系统API版本
     *
     * @return the ApiVersion of current phone,eg:API 14
     */
    public static int getPhoneAPIVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机系统版本
     *
     * @return the system version.eg:5.0.2
     */
    public static String getPhoneSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商和型号
     *
     * @return Manufacturer and model. eg: HUAWEI PLK-AL10
     */
    public static String getPhoneManufacturerAndModel() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 获取CPU型号
     *
     * @return the cpu model
     */
    public static String getCpuName() {
        String name = getCpuName1();
        if (TextUtils.isEmpty(name)) {
            name = getCpuName2();
            if (TextUtils.isEmpty(name)) {
                name = "unknown";
            }
        }
        return name;
    }

    /**
     * 方式1 获取 CPU型号
     *
     * @return cpu model
     */
    private static String getCpuName1() {
        String[] abiArr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abiArr = Build.SUPPORTED_ABIS;
        } else {
            abiArr = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }

        StringBuilder abiStr = new StringBuilder();
        for (String abi : abiArr) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        return abiStr.toString();
    }

    /**
     * 方式2获取 CPU型号
     *
     * @return cpu model
     */
    private static String getCpuName2() {
        FileReader e = null;
        BufferedReader br = null;
        try {
            e = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(e);
            String text = br.readLine();
            if (!TextUtils.isEmpty(text)) {
                String[] array = text.split(":\\s+", 2);
                e.close();
                br.close();
                return array[1];
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        } finally {
            try {
                if (e != null) {
                    e.close();
                }
                if (br != null) {
                    br.close();
                }
                return null;
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 键盘是否弹出
     *
     * @param context Application Context
     * @return Keyboard is showing
     */
    public static boolean isKeyboardShowing(Context context) {

        if (context == null) return false;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return false;
        return imm.isActive();

    }

    /**
     * 弹出键盘
     *
     * @param context  Application Context
     * @param editText the current EditText
     */
    public static void showKeyboard(Context context, EditText editText) {

        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 隐藏键盘
     *
     * @param context Application Context
     */
    public static void hideKeyboard(Context context) {

        if (!(context instanceof Activity)) return;

        Activity activity = (Activity) context;

        View v = activity.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /**
     * 获取手机屏幕宽
     *
     * @param context Application Context
     * @return the device width in px
     */
    public static int getPhoneWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取手机屏幕高
     *
     * @param context Application Context
     * @return the device height in px
     */
    public static int getPhoneHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取设备唯一标识
     * @param context Application Context
     * @return the device unique id
     *
     * 统一使用speech的方法DeviceUtil.getImei(context);
     * 避免双发deviceID不一致出现同一设备返回的数据却不一致的问题
     */

    public static String getDeviceId(Context context){
        //获取设备的唯一标识
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String serial = androidID + Build.SERIAL;
        return androidID+serial;
    }


//    public static String getDeviceId(Context context){

//        // 61bda04e7ce4
//        String deviceId = Build.SERIAL;
//
//        // 865400038522526
//        if ("".equals(deviceId) || "zeros".equals(deviceId) || "asterisks".equals(deviceId)){
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//            deviceId = tm.getDeviceId();
//        }
//
//        // 488c8ad0bbe37df4
//        if ("".equals(deviceId) || "zeros".equals(deviceId) || "asterisks".equals(deviceId)){
//            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }
//
//        return deviceId;

//        return DeviceUtil.getImei(context);
//    }
}
