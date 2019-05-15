package com.jake.experience.commonlib.system_tool;

import android.annotation.SuppressLint;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

/**
 * @author
 * @version 1.0
 * @date 19-4-8
 */
public class NetworkUtils {

    private long getNetStats(Context context , long beginTime , long endTime) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        long netDataRx = 0 ; // 接受
        long netDataTx = 0 ; // 发送
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String subID = telephonyManager.getSubscriberId();
        NetworkStatsManager manager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        NetworkStats networkStats = null;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        try {
            networkStats = manager.querySummary(NetworkCapabilities.TRANSPORT_WIFI, subID, beginTime , endTime);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        while ((networkStats.hasNextBucket())) {
            networkStats.getNextBucket(bucket);
            int uid = bucket.getUid();
            if (getUidByPackageName() == uid) {
                netDataRx += bucket.getRxBytes();
                netDataTx += bucket.getTxBytes();
            }
        }

        long  total = netDataRx + netDataTx;
        return total;
    }

    private int getUidByPackageName() {
        return 0;
    }


}
