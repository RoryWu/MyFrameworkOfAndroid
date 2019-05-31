package com.jake.app.jframework.fragments;


/**
 * @author
 * @version 1.0
 * @date 19-5-16
 */
public abstract class JFragment extends PermissionCheckFragment {
    public <T extends JFragment> T getParentDalegate() {
        return (T) getParentFragment();
    }
}
