package com.jake.app.jframework.fragments;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.jake.app.jframework.ui.CameraToolClass;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author
 * @version 1.0
 * @date 19-5-16
 */
@RuntimePermissions
public abstract class PermissionCheckFragment extends BaseFragment {

    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        CameraToolClass.start(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
