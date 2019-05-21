package com.freak.mvvmhttpmanager.mvvm.moredatabinding.view;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.freak.httpmanager.event.LiveBus;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.activity.AbstractLifecycleWithDatabindingActivity;
import com.freak.mvvmhttpmanager.databinding.ActivityMoreDataBindingBinding;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.model.MoreDataBindingModel;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.model.MoreDataBindingModel2;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.viewmodel.MoreDataBindingViewModel;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class MoreDatabindingActivity extends AbstractLifecycleWithDatabindingActivity<MoreDataBindingViewModel, ActivityMoreDataBindingBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_data_binding;
    }

    @Override
    protected void initEventAndData() {
        mViewModel.getMoreDataBinding();
        mViewModel.getMoreDataBinding2();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe(Constants.MORE, MoreDataBindingModel.class).observe(this, new Observer<MoreDataBindingModel>() {
            @Override
            public void onChanged(@Nullable MoreDataBindingModel moreDataBindingModel) {
                mDatabinding.setMoreViewDataBindingViewModel(moreDataBindingModel);
            }
        });
        LiveBus.getDefault().subscribe(Constants.MORE1, MoreDataBindingModel2.class).observe(this, new Observer<MoreDataBindingModel2>() {
            @Override
            public void onChanged(@Nullable MoreDataBindingModel2 moreDataBindingModel2) {
                mDatabinding.setDataViewModel(moreDataBindingModel2);
            }
        });
    }

    @Override
    protected void transStatusColor() {

    }
}
