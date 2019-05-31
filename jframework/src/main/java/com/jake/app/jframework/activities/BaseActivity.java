package com.jake.app.jframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;

import com.jake.app.jframework.R;
import com.jake.app.jframework.fragments.JFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author Jake
 * @version 1.0
 * @date 19-5-15
 */
public abstract class BaseActivity extends FragmentActivity implements ISupportActivity {

    private final SupportActivityDelegate mSupportActivityDelegate = new SupportActivityDelegate(this);

    public abstract JFragment setRootFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSupportActivityDelegate.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setId(R.id.delegate_id);
        setContentView(constraintLayout);
        if (savedInstanceState == null) {
            mSupportActivityDelegate.loadRootFragment(R.id.delegate_id , setRootFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSupportActivityDelegate.onDestroy();
        System.gc();
        System.runFinalization();
    }


    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mSupportActivityDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return mSupportActivityDelegate.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mSupportActivityDelegate.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mSupportActivityDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mSupportActivityDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        mSupportActivityDelegate.onBackPressedSupport();
    }
}
