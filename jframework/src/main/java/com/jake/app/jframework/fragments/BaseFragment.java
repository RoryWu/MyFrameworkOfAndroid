package com.jake.app.jframework.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jake.app.jframework.activities.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author Jake
 * @version 1.0
 * @date 19-5-15
 */
public abstract class BaseFragment extends Fragment implements ISupportFragment {

    private FragmentActivity mFragmentActivity = null;
    private final SupportFragmentDelegate mSupportFragmentDelegate = new SupportFragmentDelegate(this);
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSupportFragmentDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSupportFragmentDelegate.onAttach((Activity) context);
        mFragmentActivity = mSupportFragmentDelegate.getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportFragmentDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSupportFragmentDelegate.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSupportFragmentDelegate.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSupportFragmentDelegate.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View");
        } 
        
        // 添加butterKnife 的绑定
        mUnbinder = ButterKnife.bind(rootView);
        onBindView(savedInstanceState , rootView);
        
        return rootView;
    }

    /**
     * 在子类中绑定各个View 的ID
     */
    protected abstract void onBindView(Bundle savedInstanceState, View rootView);

    protected abstract Object setLayout() ;

    public final BaseActivity getBaseActivity() {
        return (BaseActivity) mFragmentActivity;
    }

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return mSupportFragmentDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return null;
    }

    @Override
    public void enqueueAction(Runnable runnable) {

    }

    @Override
    public void onEnterAnimationEnd(@Nullable Bundle savedInstanceState) {
        mSupportFragmentDelegate.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mSupportFragmentDelegate.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onSupportVisible() {
        mSupportFragmentDelegate.onSupportInvisible();
    }

    @Override
    public void onSupportInvisible() {
        mSupportFragmentDelegate.onSupportInvisible();
    }

    @Override
    public boolean isSupportVisible() {
        return mSupportFragmentDelegate.isSupportVisible();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mSupportFragmentDelegate.onCreateFragmentAnimator();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mSupportFragmentDelegate.getFragmentAnimator();
    }
    
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mSupportFragmentDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        mSupportFragmentDelegate.setFragmentResult(resultCode, bundle);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        mSupportFragmentDelegate.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewBundle(Bundle args) {
        mSupportFragmentDelegate.onNewBundle(args);
    }

    @Override
    public void putNewBundle(Bundle newBundle) {
        mSupportFragmentDelegate.putNewBundle(newBundle);
    }

    @Override
    public boolean onBackPressedSupport() {
        return mSupportFragmentDelegate.onBackPressedSupport();
    }
}
