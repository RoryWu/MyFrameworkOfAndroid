package com.jake.app.jframework.ui;

import android.net.Uri;

import com.jake.app.jframework.fragments.PermissionCheckFragment;
import com.jake.app.jframework.utils.file.FileUtil;

/**
 * @author
 * @version 1.0
 * @date 19-5-31
 */
public class CameraToolClass {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckFragment delegate) {
//        new CameraHandler(delegate).beginCameraDialog();
    }
    
}
