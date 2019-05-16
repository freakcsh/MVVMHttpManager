package com.freak.mvvmhttpmanager.mvvm.fregment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.base.activity.AbstractSimpleActivity;
import com.freak.mvvmhttpmanager.mvvm.fregment.view.MVVMFragment;
import com.freak.mvvmhttpmanager.util.StateUtil;


/**
 * @author Freak
 * @date 2019/5/16.
 */

public class MVVMFragmentActivity extends AbstractSimpleActivity {

    private MVVMFragment mMVVMFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvvm_fragment;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mMVVMFragment == null) {
            mMVVMFragment = new MVVMFragment();
            fragmentTransaction.add(R.id.frame_layout, mMVVMFragment, "mvvm");
        } else {
            fragmentTransaction.show(mMVVMFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void transStatusColor() {
        StateUtil.transStatusColor(this, ContextCompat.getColor(this, R.color.black));
    }
}
