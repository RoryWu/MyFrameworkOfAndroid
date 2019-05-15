package com.jake.experience.commonlib.system_tool;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;


/**
 * Created by zhanghegang
 * date : 18-4-18
 * descript:获得app信息的工具类
 */
public class AppInfoUtils {
    /**
     * 获取应用版本名，对应 app/build.gradle 的 versionName 属性
     *
     * @param context Context 对象
     * @return 版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getAppVersionName(Context context, String pkgName) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(pkgName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用版本码，对应 app/build.gradle 的 versionCode 属性
     *
     * @param context Context 对象
     * @return 版本码
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    public static int getAppVersionCode(Context context, String pkgName) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(pkgName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    //根据包名获取应用是否存在
    public static boolean isAppAvilible(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    //根据名字搜索应用是否存在
    public static boolean findAppByName(Context context, String appName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                String apname = pinfo.get(i).applicationInfo.loadLabel(packageManager).toString();
                if (appName.equals(apname)) {
                    return true;
                }
            }
        }
        return false;
    }

    //跳到单一应用市场
    public static void goToMarket(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "尚未监测到应用市场,请到网页安装", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //多个应用市场选择
    public static void goToMarket(Context context, String appPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "尚未监测到应用市场,请到网页安装", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    //多个应用市场选择，根据名字在应用市场搜索下载
    public static void downloadAppByAppName(Context context, String appName) {
        try {
            if (TextUtils.isEmpty(appName)) return;
            Uri uri = Uri.parse("market://search?q=" + appName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "尚未监测到应用市场,请到网页安装", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 获取手机指定的应用市场,可能多个，只能遍历了
     */

    public static String getDefaultMarketPackageName(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        int size = infos.size();
        String pgName = "";
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ActivityInfo activityInfo = infos.get(i).activityInfo;
                pgName = activityInfo.packageName;//如果有多个应用市场，这里只取最后一个
            }
        } else {
            Toast.makeText(context, "尚未监测到应用市场,请到网页安装", Toast.LENGTH_SHORT).show();
        }
        return pgName;
    }


    /**
     * 判断app处于后台状态
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName()
                    .equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否在主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
