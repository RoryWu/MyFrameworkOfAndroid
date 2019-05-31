package com.jake.app.jframework.fragments;

import android.Manifest;
import android.content.Intent;

import com.jake.app.jframework.ui.RequestCodes;
import com.jake.app.jframework.ui.camera.CameraToolClass;
import com.jake.app.jframework.ui.scaner.ScannerFragment;

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

    //扫描二维码(不直接调用)
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseFragment delegate) {
        delegate.getSupportDelegate().startForResult(new ScannerFragment(), RequestCodes.SCAN);
    }
    

    public void startCameraWithCheck(){
        PermissionCheckFragmentPermissionsDispatcher.startCameraWithCheck(this);
    }

    public void startScanWithCheck(BaseFragment baseFragment){
        PermissionCheckFragmentPermissionsDispatcher.startScanWithCheck(this , baseFragment);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
