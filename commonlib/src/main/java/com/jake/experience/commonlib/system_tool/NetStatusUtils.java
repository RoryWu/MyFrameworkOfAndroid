package com.jake.experience.commonlib.system_tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;


/**
 * Created by zhanghegang
 * date : 18-4-18
 * descript:检查网络状态
 */
public class NetStatusUtils {

    /**
     * 检查网络是否链接
     *
     * @return
     */
    public static boolean isConnect() {
//        synchronized (NetStatusUtils.class) {
//            if (checkPermissions(Manifest.permission.INTERNET)) {
//                ConnectivityManager systemService = (ConnectivityManager) CoreApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
//                if (null != activeNetworkInfo && activeNetworkInfo.isConnected()) {
//                    return true;
//                }
//                return false;
//
//            } else {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * 检查是否为Wifi
     *
     * @return
     */
    public static boolean isWifi() {
//        synchronized (NetStatusUtils.class) {
//            if (checkPermissions(Manifest.permission.INTERNET)) {
//                ConnectivityManager systemService = (ConnectivityManager) CoreApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                if (systemService == null)
//                    return false;
//                int type = systemService.getActiveNetworkInfo().getType();
//                return type == ConnectivityManager.TYPE_WIFI;
//
//            } else {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        if (activity == null) return;
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);

        activity.startActivity(intent);
    }

    /**
     * 在判断网络是否链接状态时，先判断网络权限是否添加
     *
     * @param
     * @param permission 被检查的权限
     * @return true 有权限 false 没权限
     */
    synchronized public static boolean checkPermissions(String permission) {
//        try {
//            PackageManager localPackageManager = CoreApp.getContext().getPackageManager();
//            return localPackageManager.checkPermission(permission,
//                    CoreApp.getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED;
//        } catch (Exception e) {
//            return false;
//        }
        return true;
    }
}
