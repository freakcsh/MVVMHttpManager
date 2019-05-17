package com.freak.mvvmhttpmanager.mvvm.adapter.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.base.activity.AbstractLifecycleActivity;
import com.freak.mvvmhttpmanager.mvvm.adapter.adapter.MVVMRecycleViewAdapter;
import com.freak.mvvmhttpmanager.mvvm.adapter.viewmodel.MVVMRecycleViewViewModel;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewActivity extends AbstractLifecycleActivity<MVVMRecycleViewViewModel> {
    private RecyclerView recycle_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycle_view;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        recycle_view = getViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(new MVVMRecycleViewAdapter());
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    protected void transStatusColor() {

    }
}
