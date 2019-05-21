package com.freak.mvvmhttpmanager.mvvm.adapter.view;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.freak.httpmanager.event.LiveBus;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.activity.AbstractLifecycleActivity;
import com.freak.mvvmhttpmanager.mvvm.adapter.adapter.MVVMRecycleViewAdapter;
import com.freak.mvvmhttpmanager.mvvm.adapter.model.MVVMRecycleViewModel;
import com.freak.mvvmhttpmanager.mvvm.adapter.viewmodel.MVVMRecycleViewViewModel;
import com.freak.mvvmhttpmanager.net.log.LogUtil;
import com.freak.mvvmhttpmanager.net.response.HttpResult;
import com.google.gson.Gson;

import java.util.List;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewActivity extends AbstractLifecycleActivity<MVVMRecycleViewViewModel> {
    private RecyclerView recycle_view;
    private MVVMRecycleViewAdapter mMVVMRecycleViewAdapter;
    private SwipeRefreshLayout swipe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycle_view;
    }

    @Override
    protected void initEventAndData() {
        mViewModel.getList();
    }

    @Override
    protected void initView() {
        recycle_view = getViewById(R.id.recycle_view);
        swipe = getViewById(R.id.swipe);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        mMVVMRecycleViewAdapter = new MVVMRecycleViewAdapter();
        recycle_view.setAdapter(mMVVMRecycleViewAdapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mViewModel.getList();
            }
        });
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe(Constants.LIST, HttpResult.class).observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                swipe.setRefreshing(false);
                if (httpResult==null){
                    LogUtil.e("没有数据");
                }else {
                    LogUtil.e(httpResult.toString());
                    List<MVVMRecycleViewModel> list=  JSONObject.parseArray(new Gson().toJson(httpResult.getData()), MVVMRecycleViewModel.class);
                    LogUtil.e(list.toString());
                    mMVVMRecycleViewAdapter.setNewData(list);
                    mMVVMRecycleViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void transStatusColor() {

    }
}
